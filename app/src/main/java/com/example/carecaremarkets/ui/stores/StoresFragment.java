package com.example.carecaremarkets.ui.stores;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carecaremarkets.CareCareMarketsDatabase;
import com.example.carecaremarkets.R;
import com.example.carecaremarkets.constants;
import com.example.carecaremarkets.daos.StoresDAO;
import com.example.carecaremarkets.databinding.FragmentStoresBinding;
import com.example.carecaremarkets.recyclerView.HomeServiceProviderRecyclerViewAdapter;
import com.example.carecaremarkets.recyclerView.ProductRecyclerViewAdapter;
import com.example.carecaremarkets.recyclerView.StoreRecyclerViewAdapter;
import com.example.carecaremarkets.tables.Products;
import com.example.carecaremarkets.tables.Stores;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

public class StoresFragment extends Fragment implements SearchView.OnQueryTextListener, CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {

    StoreRecyclerViewAdapter adapter;
    RecyclerView storesRecyclerView;
    List<Stores> stores= new ArrayList<>();
    SearchView storesSearchView;
    StoresDAO storesDAO;
    Chip topRatedChip;
    SharedPreferences pref;
    String governorate;
    ArrayAdapter<CharSequence> adapter2;

    Spinner governoratesSpinner;
    private FragmentStoresBinding binding;

public View onCreateView(@NonNull LayoutInflater inflater,
        ViewGroup container, Bundle savedInstanceState) {


    binding = FragmentStoresBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

    //setting ServiceProviderRecyclerView
    storesRecyclerView = binding.storesRecyclerView;
    GridLayoutManager layoutManager = new GridLayoutManager(root.getContext(),2);
    storesRecyclerView.setLayoutManager(layoutManager);
    adapter = new StoreRecyclerViewAdapter(stores,root.getContext());
    storesRecyclerView.setAdapter(adapter);
    getStores();
    storesSearchView = binding.storesSearchView;
    storesSearchView.setOnQueryTextListener(this);

    topRatedChip = binding.topRatedChip2;
    topRatedChip.setOnCheckedChangeListener(this);



    pref = this.getActivity().getSharedPreferences("userData", Context.MODE_PRIVATE);

    governoratesSpinner = binding.governoratesSpinner3;
    governorate = pref.getString("userGovernorate","error");
    adapter2 = ArrayAdapter.createFromResource(this.getActivity(), R.array.governorates2, android.R.layout.simple_spinner_item);
    governoratesSpinner.setAdapter(adapter2);
    if (governorate != null) {
        int spinnerPosition = adapter2.getPosition(governorate);
        governoratesSpinner.setSelection(spinnerPosition);
    }

    governoratesSpinner.setOnItemSelectedListener(this);

    return root;
}

void getStores()
{
    stores.clear();
    storesDAO = CareCareMarketsDatabase.getInstance(this.getActivity()).storesDAO();
    stores.addAll(storesDAO.getTop20Stores(constants.getGovernorate(this.getActivity())));
    adapter.notifyDataSetChanged();

}
@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if(s.isEmpty())
            getStores();
        else
        {
            storesDAO = CareCareMarketsDatabase.getInstance(this.getActivity()).storesDAO();
            stores.clear();
            stores.addAll(storesDAO.getStores(s+"%",constants.getGovernorate(this.getActivity())));
            adapter.notifyDataSetChanged();
        }
        return false;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(b)
            getTopRated();
        else
            getStores();
    }

    void getTopRated() {
        storesDAO = CareCareMarketsDatabase.getInstance(this.getActivity()).storesDAO();
        stores.clear();
        stores.addAll(storesDAO.getTopStores(constants.getGovernorate(this.getActivity())));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(i == 0)
        {
            storesDAO = CareCareMarketsDatabase.getInstance(this.getActivity()).storesDAO();
            stores.clear();
            stores.addAll(storesDAO.getTopStoresWithoutGovernorate());
            adapter.notifyDataSetChanged();
        }
        else
        {
            storesDAO = CareCareMarketsDatabase.getInstance(this.getActivity()).storesDAO();
            stores.clear();
            stores.addAll(storesDAO.getTopStores(governoratesSpinner.getSelectedItem().toString()));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}