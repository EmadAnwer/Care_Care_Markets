package com.example.carecaremarkets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carecaremarkets.daos.OrderProductsDAO;
import com.example.carecaremarkets.daos.ProductsDAO;
import com.example.carecaremarkets.daos.UserOrdersDAO;
import com.example.carecaremarkets.recyclerView.CartProductRecyclerViewAdapter;
import com.example.carecaremarkets.recyclerView.UserProductsReviewsRecyclerViewAdapter;
import com.example.carecaremarkets.tables.OrderProducts;
import com.example.carecaremarkets.tables.Products;
import com.example.carecaremarkets.tables.UserOrders;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences pref;
    NestedScrollView ordersNestedScrollView;
    TextView noItemTextView;
    ProductsDAO productsDAO;
    UserOrdersDAO userOrdersDAO;
    RecyclerView userProductsRecyclerView;
    List<Products> products= new ArrayList<>();
    Toolbar resultsToolbar;

    OrderProductsDAO orderProductsDAO;
    CartProductRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ordersNestedScrollView = findViewById(R.id.ordersNestedScrollView);
        noItemTextView = findViewById(R.id.noItemTextView);
        userProductsRecyclerView = findViewById(R.id.userProductsRecyclerView);

        resultsToolbar = findViewById(R.id.resultsToolbar3);
        resultsToolbar.setNavigationOnClickListener(this);
        getProducts();


    }

    @SuppressLint("NotifyDataSetChanged")
    private void getProducts() {
        pref = getSharedPreferences("productsInCartPref", Context.MODE_PRIVATE);
        int productsCount = pref.getInt("productsCount",-1);

        if(productsCount < 1)
        {
            ordersNestedScrollView.setVisibility(View.GONE);
            noItemTextView.setVisibility(View.VISIBLE);
            return;

        }

        for (int i = 1; i <= productsCount; i++) {
            productsDAO = CareCareMarketsDatabase.getInstance(this).productsDAO();
            int id = pref.getInt("product"+i,-1);
            products.add(productsDAO.getProduct(id));
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        userProductsRecyclerView.setLayoutManager(layoutManager);
        adapter = new CartProductRecyclerViewAdapter(products,this);
        userProductsRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }


    @Override
    public void onClick(View view) {
        super.onBackPressed();
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    public void orderProducts(View view) {
        pref = getSharedPreferences("userData", Context.MODE_PRIVATE);
        int userID = pref.getInt("userID",-1);
        userOrdersDAO = CareCareMarketsDatabase.getInstance(this).userOrdersDAO();

       long orderID = userOrdersDAO.order(new UserOrders(userID));

        orderProductsDAO = CareCareMarketsDatabase.getInstance(this).orderProductsDAO();

        for (Products product : products) {

            orderProductsDAO.orderProducts(new OrderProducts((int) orderID,product.product_id));
        }


        pref = getSharedPreferences("productsInCartPref", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = pref.edit();
        editor.putInt("productsCount",0);
        editor.apply();


        products.clear();
        adapter.notifyDataSetChanged();
        ordersNestedScrollView.setVisibility(View.GONE);
        noItemTextView.setVisibility(View.VISIBLE);
        noItemTextView.setText("We Reserved your Order We Will call you as soon as we can");



    }
}