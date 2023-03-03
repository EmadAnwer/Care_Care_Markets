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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carecaremarkets.R;
import com.example.carecaremarkets.ServiceProvidersActivity;
import com.example.carecaremarkets.constants;
import com.example.carecaremarkets.tables.ServiceProviders;
import com.example.carecaremarkets.tables.Services;

import java.util.List;



public class ServicesRecyclerViewAdapter extends RecyclerView.Adapter<ServicesRecyclerViewAdapter.ViewHolder> {
    SharedPreferences pref;
    //var
    private final List<Services> servicesArrayList;
    Context context;


    public ServicesRecyclerViewAdapter(List<Services> servicesArrayList, Context context) {
        this.servicesArrayList = servicesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_layout, parent, false);


        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {




        if (constants.LANGUAGE == EN)

        {
            holder.serviceNameTextView.setText(servicesArrayList.get(position).name_EN);
            holder.servicePriceTextView.setText(servicesArrayList.get(position).price + " LE");

        }
        else
        {
            holder.serviceNameTextView.setText(servicesArrayList.get(position).name_EN);
            holder.servicePriceTextView.setText(servicesArrayList.get(position).price + " ج");

        }

    }



    @Override
    public int getItemCount() {
        return servicesArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView serviceNameTextView, servicePriceTextView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceNameTextView = itemView.findViewById(R.id.serviceNameTextView);
            servicePriceTextView = itemView.findViewById(R.id.servicePriceTextView);
        }
    }
}