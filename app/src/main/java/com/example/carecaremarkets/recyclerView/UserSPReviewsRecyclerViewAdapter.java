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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carecaremarkets.R;
import com.example.carecaremarkets.ServiceProvidersActivity;
import com.example.carecaremarkets.constants;
import com.example.carecaremarkets.daos.UserSPR;
import com.example.carecaremarkets.tables.ServiceProviders;
import com.example.carecaremarkets.tables.Stores;

import java.util.List;


public class UserSPReviewsRecyclerViewAdapter extends RecyclerView.Adapter<UserSPReviewsRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {
    SharedPreferences pref;
    //var
    private final List<UserSPR> userSPRList;
    Context context;


    public UserSPReviewsRecyclerViewAdapter(List<UserSPR> userSPRList, Context context) {
        this.userSPRList = userSPRList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_layout, parent, false);


        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    if(userSPRList.get(position).profilePicture != null)
    {
        Bitmap bmp= BitmapFactory.decodeByteArray(userSPRList.get(position).profilePicture, 0 , userSPRList.get(position).profilePicture.length);
        holder.reviewUserProfilePictureImageView.setImageBitmap(bmp);
    }


        holder.reviewUserNameTextView.setText(" (" + userSPRList.get(position).name + ")");
        holder.reviewContentTextView.setText(userSPRList.get(position).review);

        pref = context.getSharedPreferences("userData", MODE_PRIVATE);


        if(userSPRList.get(position).id == pref.getInt("userID",0))
            holder.deleteReviewTextView.setVisibility(View.VISIBLE);
        holder.deleteReviewTextView.setTag(userSPRList.get(position).SP_id);
        holder.deleteReviewTextView.setOnClickListener(this);


        holder.reviewRatingBar.setRating((float) userSPRList.get(position).getRate());
    }

    @Override
    public void onClick(View v) {
        //TODO Delete SP Review
        int SP_id = (int) v.getTag();
        Toast.makeText(context, "Delete SP", Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getItemCount() {
        return userSPRList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView reviewUserProfilePictureImageView;
        TextView reviewUserNameTextView, reviewContentTextView;
        RatingBar reviewRatingBar;
        TextView deleteReviewTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewUserProfilePictureImageView = itemView.findViewById(R.id.reviewUserProfilePictureImageView);
            reviewUserNameTextView = itemView.findViewById(R.id.reviewUserNameTextView);
            reviewContentTextView = itemView.findViewById(R.id.reviewContentTextView);
            deleteReviewTextView = itemView.findViewById(R.id.deleteReviewTextView);
            reviewRatingBar = itemView.findViewById(R.id.reviewRatingBar);

        }
    }
}
