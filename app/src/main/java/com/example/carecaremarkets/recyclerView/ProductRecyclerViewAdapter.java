package com.example.carecaremarkets.recyclerView;

import static android.content.Context.MODE_PRIVATE;
import static com.example.carecaremarkets.constants.EN;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carecaremarkets.ProductActivity;
import com.example.carecaremarkets.R;
import com.example.carecaremarkets.constants;
import com.example.carecaremarkets.tables.Products;

import java.util.List;


public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewHolder> implements View.OnClickListener{
    SharedPreferences pref;
    //var
    private final List<Products> productsArrayList;
    Context context;


    public ProductRecyclerViewAdapter(List<Products> productsArrayList, Context context) {
        this.productsArrayList = productsArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout,parent,false);


        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Bitmap bmp= BitmapFactory.decodeByteArray(productsArrayList.get(position).product_picture, 0 , productsArrayList.get(position).product_picture.length);
        holder.productCoverImageView.setImageBitmap(bmp);

        holder.productReviewsCountTextView.setText(" ("+productsArrayList.get(position).reviews_count+")");
        holder.productPriceTextView.setText(productsArrayList.get(position).reviews_count+" LE");
        if(constants.LANGUAGE == EN)
            holder.productTextView.setText(productsArrayList.get(position).getName_EN());
        else
            holder.productTextView.setText(productsArrayList.get(position).getName_EN());

        holder.productCoverImageView.setOnClickListener(this);
        holder.productCoverImageView.setTag(productsArrayList.get(position));
        holder.productRatingBar.setRating((float) productsArrayList.get(position).getRate());
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
        TextView productTextView,productReviewsCountTextView,productPriceTextView;
        RatingBar productRatingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productReviewsCountTextView =itemView.findViewById(R.id.productReviewsCountTextView);
            productCoverImageView =itemView.findViewById(R.id.productCoverImageView);
            productRatingBar =itemView.findViewById(R.id.productRatingBar);
            productTextView =itemView.findViewById(R.id.productTextView);
            productPriceTextView =itemView.findViewById(R.id.productPriceTextView);

        }
    }
}
