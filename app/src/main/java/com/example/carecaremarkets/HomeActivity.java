package com.example.carecaremarkets;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {
    TextView usernameTextView;
    ImageView profileImageView;
    SharedPreferences pref;

    byte[] profilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        pref = getSharedPreferences("userData", Context.MODE_PRIVATE);

        //TODO get all user data
        usernameTextView = findViewById(R.id.usernameTextView);
        usernameTextView.setText(pref.getString("userName","error"));


        //TODO Get image from database
        profileImageView = findViewById(R.id.profileImageView);
        profilePicture = getIntent().getByteArrayExtra("profile_picture");
        if(profilePicture != null)
        {
            Bitmap bmp= BitmapFactory.decodeByteArray(profilePicture, 0 , profilePicture.length);
            profileImageView.setImageBitmap(bmp);
        }



        //set




    }

}