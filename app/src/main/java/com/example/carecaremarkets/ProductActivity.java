package com.example.carecaremarkets;

import static com.example.carecaremarkets.constants.EN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carecaremarkets.daos.UserPR;
import com.example.carecaremarkets.daos.UserProductsReviewsDAO;
import com.example.carecaremarkets.daos.UserSPR;
import com.example.carecaremarkets.daos.UserSPReviewsDAO;
import com.example.carecaremarkets.recyclerView.UserProductsReviewsRecyclerViewAdapter;
import com.example.carecaremarkets.tables.UserProductsReviews;
import com.example.carecaremarkets.tables.UserSPReviews;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView userSPReviewsRecyclerView;
    UserProductsReviewsRecyclerViewAdapter UserProductsReviewsRecyclerViewAdapter;
    UserProductsReviewsDAO userProductsReviewsDAO;
    CollapsingToolbarLayout productCollapsingToolbar;
    List<UserPR> userSPRList= new ArrayList<>();
    SharedPreferences pref;
    ShapeableImageView productImageView;
    TextView productPricetextView;
    TextInputLayout reviewsPostTextField;
    String name;
    int price,product_id;
    Toolbar resultsToolbar;
    
    RatingBar reviewsRatingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        //get product data
        pref = getSharedPreferences("productsPref", Context.MODE_PRIVATE);
        name = pref.getString("name","no Name");
        price = pref.getInt("price",0);
        product_id = pref.getInt("product_id",0);

        resultsToolbar = findViewById(R.id.ticketsToolbar);
        resultsToolbar.setNavigationOnClickListener(this);

        //setting Data
        productImageView = findViewById(R.id.productImageView);
        userProductsReviewsDAO = CareCareMarketsDatabase.getInstance(this).userProductsReviewsDAO();
        byte[] userPhotoArray = userProductsReviewsDAO.getProductPicture(product_id);
        if(userPhotoArray != null)
        {
            Bitmap bmp= BitmapFactory.decodeByteArray(userPhotoArray, 0 , userPhotoArray.length);
            productImageView.setImageBitmap(bmp);

        }


        reviewsRatingBar = findViewById(R.id.reviewsRatingBar);
        reviewsPostTextField = findViewById(R.id.reviewsPostTextField);

        productCollapsingToolbar = findViewById(R.id.productCollapsingToolbar);
        productCollapsingToolbar.setTitle(name);
        productPricetextView = findViewById(R.id.productPricetextView);

        if (constants.LANGUAGE == EN)
            productPricetextView.setText(price + " LE");
        else
            productPricetextView.setText(price + " ج");


        userSPReviewsRecyclerView = findViewById(R.id.userSPReviewsRecyclerView);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        userSPReviewsRecyclerView.setLayoutManager(layoutManager2);
        UserProductsReviewsRecyclerViewAdapter = new UserProductsReviewsRecyclerViewAdapter(userSPRList,this);
        userSPReviewsRecyclerView.setAdapter(UserProductsReviewsRecyclerViewAdapter);
        getSPReviews(product_id);
    }

    @SuppressLint("NotifyDataSetChanged")
    void getSPReviews(int sp_id)
    {
        userSPRList.clear();
        userProductsReviewsDAO = CareCareMarketsDatabase.getInstance(this).userProductsReviewsDAO();
        userSPRList.addAll(userProductsReviewsDAO.getAllReviews(sp_id));
        UserProductsReviewsRecyclerViewAdapter.notifyDataSetChanged();
    }
    public void addToCart(View view) {

        pref = getSharedPreferences("productsInCartPref", Context.MODE_PRIVATE);
        int productsCount = pref.getInt("productsCount",-1);
        if(productsCount == -1)
            productsCount = 0;
        else
            productsCount +=1;

        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("productsCount", productsCount);
        editor.putInt("product"+productsCount, product_id);
        editor.apply();

        Toast.makeText(this, "There are "+(productsCount)+" in the Cart", Toast.LENGTH_SHORT).show();

    }


    public void setReview(View view) {
        Button v = (Button) view;
        v.setEnabled(false);


        pref = getSharedPreferences("userData", Context.MODE_PRIVATE);
        int userID = pref.getInt("userID", 0);
        double rate = reviewsRatingBar.getRating();
        String review = reviewsPostTextField.getEditText().getText().toString();

        UserProductsReviews userSPReview = new UserProductsReviews(userID,product_id,rate,review);
        userProductsReviewsDAO.insertReview(userSPReview);
        userProductsReviewsDAO = CareCareMarketsDatabase.getInstance(this).userProductsReviewsDAO();
        getSPReviews(product_id);

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