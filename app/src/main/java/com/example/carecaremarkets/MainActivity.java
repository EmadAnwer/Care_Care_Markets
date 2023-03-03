package com.example.carecaremarkets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.carecaremarkets.daos.UsersDAO;
import com.example.carecaremarkets.tables.Users;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    public void login(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);


        // nullify
        intent = null;

    }

    public void createAnAccount(View view) {
        Intent intent = new Intent(this, CreateAnAccountActivity.class);
        startActivity(intent);


        // nullify
        intent = null;

    }

    public void back(View view) {
        super.onBackPressed();
    }
}