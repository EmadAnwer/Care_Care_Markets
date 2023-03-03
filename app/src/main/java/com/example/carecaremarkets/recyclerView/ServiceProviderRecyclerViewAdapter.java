
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

import com.bumptech.glide.Glide;
import com.example.carecaremarkets.R;
import com.example.carecaremarkets.ServiceProvidersActivity;
import com.example.carecaremarkets.constants;
import com.example.carecaremarkets.tables.ServiceProviders;

import java.util.List;

public class ServiceProviderRecyclerViewAdapter extends RecyclerView.Adapter<ServiceProviderRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {
    SharedPreferences pref;
    //var
    private final List<ServiceProviders> serviceProvidersArrayList;
    Context context;


    public ServiceProviderRecyclerViewAdapter(List<ServiceProviders> governorateArrayList, Context context) {
        this.serviceProvidersArrayList = governorateArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_provider_home_layout, parent, false);


        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Bitmap bmp= BitmapFactory.decodeByteArray(serviceProvidersArrayList.get(position).SP_picture, 0 , serviceProvidersArrayList.get(position).SP_picture.length);
        holder.serviceProviderCoverImageView.setImageBitmap(bmp);

        holder.serviceProviderReviewsCountTextView.setText(" (" + serviceProvidersArrayList.get(position).reviews_count + ")");
        if (constants.LANGUAGE == EN)
            holder.serviceProviderTextView.setText(serviceProvidersArrayList.get(position).getName_EN());
        else
            holder.serviceProviderTextView.setText(serviceProvidersArrayList.get(position).getName_AR());
        holder.serviceProviderRatingBar.setRating(serviceProvidersArrayList.get(position).getRate());

        holder.serviceProviderCoverImageView.setOnClickListener(this);
        holder.serviceProviderCoverImageView.setTag(serviceProvidersArrayList.get(position));
    }

    @Override
    public void onClick(View v) {
        //TODO
        ServiceProviders s = (ServiceProviders) v.getTag();

        // pass ServiceProvider Data within SharedPreferences
        pref = context.getSharedPreferences("serviceProviderPref", MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = pref.edit();

        editor.putInt("sp_id", s.sp_id);
        if (constants.LANGUAGE == EN)
        {
            editor.putString("name", s.name_EN);
            editor.putString("address", s.address_EN);
        }
        else {
            editor.putString("name", s.name_AR);
            editor.putString("address", s.address_AR);
        }
        editor.putString("email", s.email);
        editor.putString("phone", s.phone);
        editor.apply();


        // intent to ServiceProvidersActivity
        Intent intent = new Intent(context, ServiceProvidersActivity.class);
        context.startActivity(intent);
        intent = null;


    }

    @Override
    public int getItemCount() {
        return serviceProvidersArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView serviceProviderCoverImageView;
        TextView serviceProviderTextView, serviceProviderReviewsCountTextView;
        RatingBar serviceProviderRatingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceProviderReviewsCountTextView = itemView.findViewById(R.id.serviceProviderReviewsCountTextView);
            serviceProviderCoverImageView = itemView.findViewById(R.id.serviceProviderCoverImageView);
            serviceProviderRatingBar = itemView.findViewById(R.id.serviceProviderRatingBar);
            serviceProviderTextView = itemView.findViewById(R.id.serviceProviderTextView);
        }
    }
}
