package com.example.carecaremarkets;

import static com.example.carecaremarkets.constants.LOGGED_IN;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.carecaremarkets.daos.UsersDAO;
import com.example.carecaremarkets.tables.Users;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout loginEmailTextField,loginPasswordTextField;
    SharedPreferences pref;
    Button loginButton;
    Intent intent;
    UsersDAO usersDAO;
    Users user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pref = getSharedPreferences("userData", Context.MODE_PRIVATE);
        loginEmailTextField = findViewById(R.id.loginEmailTextField);
        loginPasswordTextField = findViewById(R.id.passwordTextField);
        loginButton = findViewById(R.id.loginButton);

    }
    public void login(View view){
        //checking email validation
        if (!(Patterns.EMAIL_ADDRESS.matcher(loginEmailTextField.getEditText().getText().toString()).matches())) {
            loginEmailTextField.setErrorEnabled(true);
            loginEmailTextField.setError(getString(R.string.wrong_email));
            loginButton.setEnabled(true);
            return;

        }

        //checking email validation
        if (loginPasswordTextField.getEditText().getText().length() < 8) {
            loginPasswordTextField.setErrorEnabled(true);
            loginPasswordTextField.setError(getString(R.string.wrong_password));
            loginButton.setEnabled(true);
            return;

        }
        String email = loginEmailTextField.getEditText().getText().toString();
        String password = loginPasswordTextField.getEditText().getText().toString();
        usersDAO = CareCareMarketsDatabase.getInstance(this).usersDAO();
        user = usersDAO.login(email,password);
        if(user == null)
        {
            Toast.makeText(this,getString( R.string.Invalid_email_password), Toast.LENGTH_SHORT).show();
            return;
        }

        loginButton.setEnabled(false);

        //save user data
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("userName", user.first_name);
        editor.putString("userEmail", user.email);
        editor.putString("userPhone", user.phone);
        editor.putString("userGovernorate", user.governorate);
        editor.putString("userAddress", user.address);
        editor.putInt("userID", user.user_id);
        editor.putInt("userLogin", LOGGED_IN);
        editor.apply();
        intent = new Intent(this, MainHomeActivity.class);
        startActivity(intent);
        intent = null;
        finishAffinity();

    }


    public void back(View view) {
        super.onBackPressed();
    }
}