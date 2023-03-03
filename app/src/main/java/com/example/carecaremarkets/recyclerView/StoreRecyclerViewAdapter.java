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
import com.example.carecaremarkets.ServiceProvidersActivity;
import com.example.carecaremarkets.StoresActivity;
import com.example.carecaremarkets.constants;
import com.example.carecaremarkets.tables.ServiceProviders;
import com.example.carecaremarkets.tables.Stores;

import java.util.List;



public class StoreRecyclerViewAdapter extends RecyclerView.Adapter<StoreRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {

    SharedPreferences pref;
    //var
    private final List<Stores> storesArrayList;
    Context context;


    public StoreRecyclerViewAdapter(List<Stores> storesArrayList, Context context) {
        this.storesArrayList = storesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_layout, parent, false);


        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Bitmap bmp= BitmapFactory.decodeByteArray(storesArrayList.get(position).store_picture, 0 , storesArrayList.get(position).store_picture.length);
        holder.storeImageView.setImageBitmap(bmp);

        holder.storeReviewsCountTextView.setText(" (" + storesArrayList.get(position).reviews_count + ")");
        if (constants.LANGUAGE == EN)
            holder.storeTextView.setText(storesArrayList.get(position).getName_EN());
        else
            holder.storeTextView.setText(storesArrayList.get(position).getName_EN());

        holder.storeImageView.setOnClickListener(this);
        holder.storeImageView.setTag(storesArrayList.get(position));
        holder.storeProviderRatingBar.setRating((float) storesArrayList.get(position).getRate());
    }

    @Override
    public void onClick(View v) {
        //TODO
        Stores s = (Stores) v.getTag();

        // pass governorate name and governorate cover within SharedPreferences
        pref = context.getSharedPreferences("storesPref", MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = pref.edit();

        editor.putInt("store_id", s.store_id);
        if (constants.LANGUAGE == EN)
            editor.putString("name", s.name_EN);
        else
            editor.putString("name", s.name_AR);

        editor.apply();



        // intent to PlacesActivity
        Intent intent = new Intent(context, StoresActivity.class);
        context.startActivity(intent);
        intent = null;


    }

    @Override
    public int getItemCount() {
        return storesArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView storeImageView;
        TextView storeTextView, storeReviewsCountTextView;
        RatingBar storeProviderRatingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            storeReviewsCountTextView = itemView.findViewById(R.id.storeReviewsCountTextView);
            storeImageView = itemView.findViewById(R.id.storeCoverImageView);
            storeProviderRatingBar = itemView.findViewById(R.id.storeRatingBar);
            storeTextView = itemView.findViewById(R.id.storeTextView);
        }
    }
}
