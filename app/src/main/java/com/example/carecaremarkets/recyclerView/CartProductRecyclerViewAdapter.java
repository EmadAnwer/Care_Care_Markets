package com.example.carecaremarkets.recyclerView;

import static android.content.Context.MODE_PRIVATE;
import static com.example.carecaremarkets.constants.EN;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carecaremarkets.ProductActivity;
import com.example.carecaremarkets.R;
import com.example.carecaremarkets.constants;
import com.example.carecaremarkets.tables.Products;

import java.util.List;




public class CartProductRecyclerViewAdapter extends RecyclerView.Adapter<CartProductRecyclerViewAdapter.ViewHolder> implements View.OnClickListener{
    SharedPreferences pref;
    //var
    private final List<Products> productsArrayList;
    Context context;


    public CartProductRecyclerViewAdapter(List<Products> productsArrayList, Context context) {
        this.productsArrayList = productsArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_product_layout,parent,false);


        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(productsArrayList.get(position).product_picture != null)
        {
            Bitmap bmp= BitmapFactory.decodeByteArray(productsArrayList.get(position).product_picture, 0 , productsArrayList.get(position).product_picture.length);
            holder.productCoverImageView.setImageBitmap(bmp);
        }


        holder.productReviewsCountTextView.setText(" ("+productsArrayList.get(position).reviews_count+")");
        holder.productPriceTextView.setText(productsArrayList.get(position).reviews_count+" LE");
        if(constants.LANGUAGE == EN)
            holder.productTextView.setText(productsArrayList.get(position).getName_EN());
        else
            holder.productTextView.setText(productsArrayList.get(position).getName_EN());

        holder.productCoverImageView.setOnClickListener(this);
        holder.productCoverImageView.setTag(productsArrayList.get(position));
        holder.productRatingBar.setRating((float) productsArrayList.get(position).getRate());

        holder.deleteReviewTextView2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                pref = context.getSharedPreferences("productsInCartPref", MODE_PRIVATE);
                @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = pref.edit();
                editor.remove("product"+position);
                int c = (pref.getInt("productsCount",-1)-1);
                editor.putInt("productsCount", c);
                editor.apply();


                productsArrayList.remove(productsArrayList.get(position));
                CartProductRecyclerViewAdapter.super.notifyDataSetChanged();
                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();

            }
        });
    }
    @Override
    public void onClick(View v) {
        //TODO
        Products s = (Products) v.getTag();
        // pass Products data within SharedPreferences

        pref = context.getSharedPreferences("productsPref", MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = pref.edit();

        editor.putInt("product_id", s.product_id);
        if (constants.LANGUAGE == EN)
            editor.putString("name", s.name_EN);
        else
            editor.putString("name", s.name_AR);

        editor.putInt("price", s.price);
        editor.apply();


        // intent to PlacesActivity
        Intent intent = new Intent(context, ProductActivity.class);
        context.startActivity(intent);
        intent = null;


    }

    @Override
    public int getItemCount() {
        return productsArrayList.size();
    }
    public static class ViewHolder  extends RecyclerView.ViewHolder{
        ImageView productCoverImageView;
        TextView productTextView,productReviewsCountTextView,productPriceTextView,deleteReviewTextView2;
        RatingBar productRatingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productReviewsCountTextView =itemView.findViewById(R.id.productReviewsCountTextView);
            productCoverImageView =itemView.findViewById(R.id.productCoverImageView);
            productRatingBar =itemView.findViewById(R.id.productRatingBar);
            productTextView =itemView.findViewById(R.id.productTextView);
            productPriceTextView =itemView.findViewById(R.id.productPriceTextView);
            deleteReviewTextView2 =itemView.findViewById(R.id.deleteReviewTextView2);


        }
    }
}
