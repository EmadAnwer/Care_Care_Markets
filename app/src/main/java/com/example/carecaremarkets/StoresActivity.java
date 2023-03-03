package com.example.carecaremarkets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import com.example.carecaremarkets.daos.ProductsDAO;
import com.example.carecaremarkets.daos.UserPR;
import com.example.carecaremarkets.daos.UserSR;
import com.example.carecaremarkets.daos.UserStoresReviewsDAO;
import com.example.carecaremarkets.recyclerView.ProductRecyclerViewAdapter;
import com.example.carecaremarkets.recyclerView.UserProductsReviewsRecyclerViewAdapter;
import com.example.carecaremarkets.recyclerView.UserStoresReviewsRecyclerViewAdapter;
import com.example.carecaremarkets.tables.Products;
import com.example.carecaremarkets.tables.UserProductsReviews;
import com.example.carecaremarkets.tables.UserSPReviews;
import com.example.carecaremarkets.tables.UserStoresReviews;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class StoresActivity extends AppCompatActivity implements View.OnClickListener {
    CollapsingToolbarLayout resultsCollapsingToolbar ;
    ProductsDAO productsDAO;
    String name;
    int store_id;
    SharedPreferences pref;
    ProductRecyclerViewAdapter adapter;
    RecyclerView storeProductsRecyclerView,userStoreReviewsRecyclerView;
    List<UserSR> userSPRList= new ArrayList<>();
    UserStoresReviewsRecyclerViewAdapter userStoresReviewsRecyclerViewAdapter;
    List<Products> products= new ArrayList<>();
    UserStoresReviewsDAO userStoresReviewsDAO;
    RatingBar reviewsRatingBar;
    TextInputLayout reviewsPostTextField;
    Toolbar resultsToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores);

        resultsToolbar = findViewById(R.id.resultsToolbar);
        resultsToolbar.setNavigationOnClickListener(this);
        reviewsRatingBar = findViewById(R.id.reviewsRatingBar);
        reviewsPostTextField = findViewById(R.id.reviewsPostTextField);


        //get product data
        pref = getSharedPreferences("storesPref", Context.MODE_PRIVATE);
        store_id = pref.getInt("store_id",0);


        //set store name
        resultsCollapsingToolbar = findViewById(R.id.resultsCollapsingToolbar);
        name = pref.getString("name","no Name");
        resultsCollapsingToolbar.setTitle(name);

        //get store products
        storeProductsRecyclerView = findViewById(R.id.storeProductsRecyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        storeProductsRecyclerView.setLayoutManager(layoutManager);
        adapter = new ProductRecyclerViewAdapter(products,this);
        storeProductsRecyclerView.setAdapter(adapter);
        getProducts();


        //get reviews
        userStoreReviewsRecyclerView = findViewById(R.id.userStoreReviewsRecyclerView);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        userStoreReviewsRecyclerView.setLayoutManager(layoutManager2);
        userStoresReviewsRecyclerViewAdapter = new UserStoresReviewsRecyclerViewAdapter(userSPRList,this);
        userStoreReviewsRecyclerView.setAdapter(userStoresReviewsRecyclerViewAdapter);
        getSPReviews(store_id);

    }


    void getProducts()
    {
        productsDAO = CareCareMarketsDatabase.getInstance(this).productsDAO();
        products.addAll(productsDAO.getRandomProducts());
        adapter.notifyDataSetChanged();

    }


    @SuppressLint("NotifyDataSetChanged")
    void getSPReviews(int store_id)
    {
        userSPRList.clear();
        userStoresReviewsDAO = CareCareMarketsDatabase.getInstance(this).userStoresReviewsDAO();
        userSPRList.addAll(userStoresReviewsDAO.getAllReviews(store_id));
        userStoresReviewsRecyclerViewAdapter.notifyDataSetChanged();
    }


    public void setReview(View view) {
        Button v = (Button) view;
        v.setEnabled(false);


        pref = getSharedPreferences("userData", Context.MODE_PRIVATE);
        int userID = pref.getInt("userID", 0);
        double rate = reviewsRatingBar.getRating();
        String review = reviewsPostTextField.getEditText().getText().toString();

        UserStoresReviews userStoresReview = new UserStoresReviews(userID,store_id,rate,review);
        userStoresReviewsDAO.insertReview(userStoresReview);
        userStoresReviewsDAO = CareCareMarketsDatabase.getInstance(this).userStoresReviewsDAO();
        getSPReviews(store_id);

        reviewsRatingBar.setRating(0);
        reviewsPostTextField.getEditText().setText("");
        v.setEnabled(true);

    }

    @Override
    public void onClick(View view) {
        super.onBackPressed();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}