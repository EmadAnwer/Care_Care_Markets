package com.example.carecaremarkets.ui.home;

import static com.example.carecaremarkets.constants.AR;
import static com.example.carecaremarkets.constants.EN;
import static com.example.carecaremarkets.constants.LOGGED_OUT;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carecaremarkets.CareCareMarketsDatabase;
import com.example.carecaremarkets.CartActivity;
import com.example.carecaremarkets.HomeActivity;
import com.example.carecaremarkets.MainActivity;
import com.example.carecaremarkets.ProfileSettingsActivity;
import com.example.carecaremarkets.R;
import com.example.carecaremarkets.UserOrdersActivity;
import com.example.carecaremarkets.constants;
import com.example.carecaremarkets.daos.ProductsDAO;
import com.example.carecaremarkets.daos.ServiceProvidersDAO;
import com.example.carecaremarkets.daos.UsersDAO;
import com.example.carecaremarkets.databinding.FragmentHomeBinding;
import com.example.carecaremarkets.recyclerView.HomeServiceProviderRecyclerViewAdapter;
import com.example.carecaremarkets.recyclerView.ProductRecyclerViewAdapter;
import com.example.carecaremarkets.tables.Products;
import com.example.carecaremarkets.tables.ServiceProviders;
import com.example.carecaremarkets.tables.UserOrders;
import com.google.android.material.imageview.ShapeableImageView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {

    private FragmentHomeBinding binding;
    SharedPreferences pref;
    UsersDAO usersDAO;
    ServiceProvidersDAO providersDAO;
    ProductsDAO productsDAO;
    ShapeableImageView profilePictureImageView;
    TextView userNameFragmentTextView,welcomeTextView;
    HomeServiceProviderRecyclerViewAdapter adapter;
    ProductRecyclerViewAdapter adapter2;
    String governorate;
    RecyclerView serviceProviderRecyclerView,productsRecyclerView;

    List<ServiceProviders> serviceProviders= new ArrayList<>();
    List<Products> products= new ArrayList<>();
    ImageView cartImageView;
    PopupMenu options;

public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

    binding = FragmentHomeBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

    pref = this.getActivity().getSharedPreferences("userData", Context.MODE_PRIVATE);
    //set welcome
    welcomeTextView = binding.welcomeTextView;
    welcomeTextView.setText(getTimeMessage());

    cartImageView = binding.cartImageView;
    cartImageView.setOnClickListener(view -> {
        Intent intent;

        intent = new Intent(getActivity(), CartActivity.class);
        startActivity(intent);
        intent = null;
    });


    //TODO get all user data
    userNameFragmentTextView = binding.userNameFragmentTextView;
    userNameFragmentTextView.setText(pref.getString("userName","error"));
    governorate = pref.getString("userGovernorate","error");
    //set profile Pic
    profilePictureImageView =  binding.profilePictureImageView;
    usersDAO = CareCareMarketsDatabase.getInstance(this.getActivity()).usersDAO();
    byte[] userPhotoArray = usersDAO.getUserPhoto(pref.getInt("userID",-1));
    if(userPhotoArray != null)
    {
        Bitmap bmp= BitmapFactory.decodeByteArray(userPhotoArray, 0 , userPhotoArray.length);
        profilePictureImageView.setImageBitmap(bmp);

    }

    profilePictureImageView.setOnClickListener(v -> {
        int lang = pref.getInt("appLanguage", 0);

        options = null;
        options = new PopupMenu(this.getContext(),v);
        options.setOnMenuItemClickListener(this);
        options.inflate(R.menu.home_options_menu);
        if(lang != -1)
        {
            final MenuItem language = options.getMenu().getItem(1).getSubMenu().getItem(lang);
            language.setCheckable(true);
            language.setChecked(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            options.setForceShowIcon(true);
        }
        else
        {
            try {
                Field popupField = PopupMenu.class.getDeclaredField("mPopup");
                popupField.setAccessible(true);
                Object o = popupField.get(options);
                assert o != null;
                o.getClass()
                        .getDeclaredMethod("setForceShowIcon", boolean.class)
                        .invoke(o,true);
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        options.show();
    });


    //setting ServiceProviderRecyclerView
    serviceProviderRecyclerView = binding.serviceProviderRecyclerView;
    LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext(),LinearLayoutManager.HORIZONTAL,false);
    serviceProviderRecyclerView.setLayoutManager(layoutManager);
    adapter = new HomeServiceProviderRecyclerViewAdapter(serviceProviders,root.getContext());
    serviceProviderRecyclerView.setAdapter(adapter);
    getServiceProviders();

    //setting productsRecyclerView
    productsRecyclerView = binding.productsRecyclerView;
    GridLayoutManager layoutManager2 = new GridLayoutManager(root.getContext(),2);
    productsRecyclerView.setLayoutManager(layoutManager2);
    adapter2 = new ProductRecyclerViewAdapter(products,root.getContext());
    productsRecyclerView.setAdapter(adapter2);
    getProducts();


        return root;
    }

    String getTimeMessage()
    {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay < 12){
            return getString(R.string.good_morning);
        }else if(timeOfDay < 16){
            return getString(R.string.good_afternoon);

        }else if(timeOfDay < 21){
            return getString(R.string.good_evening);
        }else {
            return getString(R.string.good_night);
        }
    }

    void getServiceProviders()
    {
        providersDAO = CareCareMarketsDatabase.getInstance(this.getActivity()).serviceProvidersDAO();
        serviceProviders.addAll(providersDAO.getTop10ServiceProviders(constants.getGovernorate(this.getActivity())));
        adapter.notifyDataSetChanged();

    }

    void getProducts()
    {
        productsDAO = CareCareMarketsDatabase.getInstance(this.getActivity()).productsDAO();
        products.addAll(productsDAO.getTop20Products());
        adapter2.notifyDataSetChanged();

    }
@Override
    public void onDestroyView() {
    serviceProviders.clear();
    products.clear();
        super.onDestroyView();
        binding = null;


    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.profileSettingsItem)
        {
            Intent intent;
            intent = new Intent(getActivity(), ProfileSettingsActivity.class);
            startActivity(intent);
            intent = null;
        }


        else if(id == R.id.languagesItemAR)
        {
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("appLanguage", AR);
            editor.apply();
            getActivity().finish();

            startActivity(this.getActivity().getIntent());

        }
        else if(id == R.id.languagesItemEN)
        {
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("appLanguage", EN);
            editor.apply();
            getActivity().finish();
            startActivity(this.getActivity().getIntent());
        }
        else if(id == R.id.logoutItem)
            userLogout();
        return true;
    }

    void userLogout()
    {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("userLogin",LOGGED_OUT);
        editor.apply();
        Intent intent = new Intent(this.getActivity(), MainActivity.class);
        startActivity(intent);
        intent = null;
        Objects.requireNonNull(this.getActivity()).finishAffinity();

    }
}