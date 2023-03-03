package com.example.carecaremarkets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.carecaremarkets.daos.ServicesDAO;
import com.example.carecaremarkets.daos.UserSPR;
import com.example.carecaremarkets.daos.UserSPReviewsDAO;
import com.example.carecaremarkets.recyclerView.ServiceProviderRecyclerViewAdapter;
import com.example.carecaremarkets.recyclerView.ServicesRecyclerViewAdapter;
import com.example.carecaremarkets.recyclerView.UserSPReviewsRecyclerViewAdapter;
import com.example.carecaremarkets.tables.ServiceProviders;
import com.example.carecaremarkets.tables.Services;
import com.example.carecaremarkets.tables.UserSPReviews;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class ServiceProvidersActivity extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences pref;
    int sp_id;
    String name,address,email,phone;
    Toolbar resultsToolbar;
    RatingBar reviewsRatingBar;
    RecyclerView servicesRecyclerView,userSPReviewsRecyclerView;
    ServicesRecyclerViewAdapter  servicesRecyclerViewAdapter ;
    UserSPReviewsRecyclerViewAdapter userSPReviewsRecyclerViewAdapter;
    TextInputLayout reviewsPostTextField;
    UserSPReviewsDAO userSPReviewsDAO;
    ServicesDAO servicesDAO;

    CollapsingToolbarLayout resultsCollapsingToolbar;
    List<Services> services= new ArrayList<>();
    List<UserSPR> userSPRList= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_providers);
        pref = getSharedPreferences("serviceProviderPref", Context.MODE_PRIVATE);
        sp_id = pref.getInt("sp_id", 0);
        name = pref.getString("name","no Name");
        email = pref.getString("email","no Email");
        phone = pref.getString("phone","no phone");
        address = pref.getString("address","no Address");
        resultsCollapsingToolbar = findViewById(R.id.resultsCollapsingToolbar);
        resultsCollapsingToolbar.setTitle(name);
        servicesRecyclerView = findViewById(R.id.servicesRecyclerView);
        reviewsRatingBar = findViewById(R.id.reviewsRatingBar);
        reviewsPostTextField = findViewById(R.id.reviewsPostTextField);

        resultsToolbar = findViewById(R.id.resultsToolbar);
        resultsToolbar.setNavigationOnClickListener(this);
        userSPReviewsRecyclerView = findViewById(R.id.userSPReviewsRecyclerView);
        //setting ServiceProviderRecyclerView

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        servicesRecyclerView.setLayoutManager(layoutManager);
        servicesRecyclerViewAdapter = new ServicesRecyclerViewAdapter(services,this);
        servicesRecyclerView.setAdapter(servicesRecyclerViewAdapter);


        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        userSPReviewsRecyclerView.setLayoutManager(layoutManager2);
        userSPReviewsRecyclerViewAdapter = new UserSPReviewsRecyclerViewAdapter(userSPRList,this);
        userSPReviewsRecyclerView.setAdapter(userSPReviewsRecyclerViewAdapter);

        Toast.makeText(this, "SP_ISD"+sp_id, Toast.LENGTH_SHORT).show();
        getServices(sp_id);
        getSPReviews(sp_id);


    }


    @SuppressLint("NotifyDataSetChanged")
    void getServices(int sp_id)
    {
        services.clear();
        servicesDAO = CareCareMarketsDatabase.getInstance(this).servicesDAO();
        services.addAll(servicesDAO.getServices(sp_id));
        servicesRecyclerViewAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    void getSPReviews(int sp_id)
    {
        userSPRList.clear();
        userSPReviewsDAO = CareCareMarketsDatabase.getInstance(this).userSPReviewsDAO();
        userSPRList.addAll(userSPReviewsDAO.getAllReviews(sp_id));
        userSPReviewsRecyclerViewAdapter.notifyDataSetChanged();
    }
    public void navigateToPhone(View view) {

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phone));
        startActivity(intent);

    }

    public void navigateToEmail(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("mailto:"+email));

        startActivity(intent);

    }

    public void navigateToLocation(View view) {
        Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/"+address));
        startActivity(in);
    }

    @Override
    public void onClick(View view) {
        super.onBackPressed();
    }

    public void setReview(View view) {
        Button v = (Button) view;
        v.setEnabled(false);


        pref = getSharedPreferences("userData", Context.MODE_PRIVATE);
        int userID = pref.getInt("userID", 0);
        double rate = reviewsRatingBar.getRating();
        String review = reviewsPostTextField.getEditText().getText().toString();

        UserSPReviews userSPReview = new UserSPReviews(userID,sp_id,rate,review);
        userSPReviewsDAO.insertReview(userSPReview);
        userSPReviewsDAO = CareCareMarketsDatabase.getInstance(this).userSPReviewsDAO();
        getSPReviews(sp_id);

        reviewsRatingBar.setRating(0);
        reviewsPostTextField.getEditText().setText("");
        v.setEnabled(true);

    }
}