package com.example.carecaremarkets;

import static com.example.carecaremarkets.constants.LOGGED_IN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.carecaremarkets.daos.UsersDAO;
import com.example.carecaremarkets.tables.Users;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;

public class CreateAnAccountActivity extends AppCompatActivity implements TextWatcher, View.OnFocusChangeListener {
    SharedPreferences pref;
    TextInputLayout nameTextField, emailTextField, passwordInputLayout, confirmPasswordTextField, phoneTextField;
    TextInputEditText selectedTextInputField ;
    ConstraintLayout root;
    Button createAnAccountButton;
    ArrayList<String> mails = new ArrayList<>();

    UsersDAO usersDAO;
    Users user;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_an_account);

        pref = getSharedPreferences("userData", Context.MODE_PRIVATE);
        //setting ids for views
        root = findViewById(R.id.root);
        nameTextField = findViewById(R.id.nameTextField);
        emailTextField = findViewById(R.id.emailTextField);
        passwordInputLayout = findViewById(R.id.passwordTextField);
        confirmPasswordTextField = findViewById(R.id.confirmPasswordTextField);
        phoneTextField = findViewById(R.id.phoneTextField);
        createAnAccountButton = findViewById(R.id.createAccountButton);

        //set OnFocusChangeListener
        Objects.requireNonNull(nameTextField.getEditText()).setOnFocusChangeListener(this);
        Objects.requireNonNull(emailTextField.getEditText()).setOnFocusChangeListener(this);
        Objects.requireNonNull(passwordInputLayout.getEditText()).setOnFocusChangeListener(this);
        Objects.requireNonNull(confirmPasswordTextField.getEditText()).setOnFocusChangeListener(this);
        Objects.requireNonNull(phoneTextField.getEditText()).setOnFocusChangeListener(this);


        //set TextChangedListener
        nameTextField.getEditText().addTextChangedListener(this);
        emailTextField.getEditText().addTextChangedListener(this);
        passwordInputLayout.getEditText().addTextChangedListener(this);
        confirmPasswordTextField.getEditText().addTextChangedListener(this);
        phoneTextField.getEditText().addTextChangedListener(this);


    }


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if (selectedTextInputField == null)
            return;

        //to checking password and confirmPassword TextField at the same time
        if (selectedTextInputField.getId() == passwordInputLayout.getEditText().getId()) {
            if (!confirmPasswordTextField.getEditText().getText().toString().isEmpty()) {
                if (confirmPasswordTextField.getEditText().getText().toString().equals(s.toString())) {
                    confirmPasswordTextField.setErrorEnabled(false);
                    confirmPasswordTextField.setErrorEnabled(true);
                } else
                    confirmPasswordTextField.setError(getString(R.string.passwords_not_same));


            }

        }

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (selectedTextInputField == null)
            return;

        /*
        name TextField validation
        -------------------------
            name is valid if
                - more than 3 char
                - in alphabet
         */

        if (selectedTextInputField.getId() == nameTextField.getEditText().getId()) {
            if (s.length() == 0) {
                nameTextField.setErrorEnabled(false);
                nameTextField.setErrorEnabled(true);

                return;
            }

            if (s.length() < 3) {
                nameTextField.setError(getString(R.string.name_is_less_than_3));
                return;
            }


            nameTextField.setErrorEnabled(false);
            nameTextField.setErrorEnabled(true);


        }


        /*
        email TextField validation
        -------------------------
            email is valid if
                - follows Patterns.EMAIL_ADDRESS
         */
        else if (selectedTextInputField.getId() == emailTextField.getEditText().getId()) {
            if (s.length() == 0) {
                emailTextField.setErrorEnabled(false);
                emailTextField.setErrorEnabled(true);

                return;
            }


            if (!(Patterns.EMAIL_ADDRESS.matcher(s).matches())) {

                emailTextField.setError("' " + s + " ' " +getString(R.string.email_is_not_valid));
                return;

            }


            emailTextField.setErrorEnabled(false);
            emailTextField.setErrorEnabled(true);
        }


        /*
        password TextField validation
        -------------------------
            password is valid if
                - more than 8 char

         */


        else if (selectedTextInputField.getId() == passwordInputLayout.getEditText().getId()) {
            if (s.length() == 0) {
                passwordInputLayout.setErrorEnabled(false);
                passwordInputLayout.setErrorEnabled(true);

                return;
            }


            if (s.length() < 8) {

                passwordInputLayout.setError(getString(R.string.password_helper));
                return;

            }

            passwordInputLayout.setErrorEnabled(false);
            passwordInputLayout.setErrorEnabled(true);
        }


        /*
        confirmPassword TextField validation
        -------------------------
            confirmPassword is valid if
                - confirmPassword == password

         */
        else if (selectedTextInputField.getId() == confirmPasswordTextField.getEditText().getId()) {
            if (s.length() == 0) {
                confirmPasswordTextField.setErrorEnabled(false);
                confirmPasswordTextField.setErrorEnabled(true);

                return;
            }


            if (!passwordInputLayout.getEditText().getText().toString().equals(s.toString())) {

                confirmPasswordTextField.setError(getString(R.string.passwords_not_same));
                return;

            }

            confirmPasswordTextField.setErrorEnabled(false);
            confirmPasswordTextField.setErrorEnabled(true);
        }


        /*
        phone TextField validation
        -------------------------
            phone is valid if
                - isValidFullNumber() = true

         */
        else if (selectedTextInputField.getId() == phoneTextField.getEditText().getId()) {
            if (s.length() == 0) {
                phoneTextField.setErrorEnabled(false);
                phoneTextField.setErrorEnabled(true);

                return;
            }


            if (s.length() != 11) {

                phoneTextField.setError(getString(R.string.phone_invalid));
                return;

            }

            phoneTextField.setErrorEnabled(false);
            phoneTextField.setErrorEnabled(true);
        }

    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        //getting selected InputField reference to use it in checking validation
        if (hasFocus) {
            selectedTextInputField = (TextInputEditText) v;
        }

    }











    boolean checkingAllFieldsValidation() {
        if (nameTextField.getEditText().getText().length() < 3)
            return false;
        if (!(Patterns.EMAIL_ADDRESS.matcher(emailTextField.getEditText().getText().toString()).matches()))
            return false;
        if (passwordInputLayout.getEditText().getText().length() < 8)
            return false;
        if (!passwordInputLayout.getEditText().getText().toString().equals(confirmPasswordTextField.getEditText().getText().toString()))
            return false;
        return phoneTextField.getEditText().getText().length() == 11;
    }

    public void back(View view) {
        super.onBackPressed();
    }

    public void create(View view) {
        createAnAccountButton.setEnabled(!checkingAllFieldsValidation());
        if (checkingAllFieldsValidation()) {
            if (mails.contains(emailTextField.getEditText().getText().toString())) {
                emailAlreadyExists();
                return;
            }

            //check Mail
            usersDAO = CareCareMarketsDatabase.getInstance(this).usersDAO();
            user = usersDAO.checkEmail(emailTextField.getEditText().getText().toString());

            if(user == null)
            {
                //create user
                user = new Users(nameTextField.getEditText().getText().toString(),
                        emailTextField.getEditText().getText().toString(),
                        passwordInputLayout.getEditText().getText().toString(),
                        phoneTextField.getEditText().getText().toString());
                usersDAO.createAnAccount(user);

                //login
                user = usersDAO.login(emailTextField.getEditText().getText().toString(),
                        passwordInputLayout.getEditText().getText().toString());


                //save user data
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("userName", user.first_name);
                editor.putString("userEmail", user.email);
                editor.putString("userPhone", user.phone);
                editor.putInt("userID", user.user_id);
                editor.putInt("userLogin", LOGGED_IN);
                editor.apply();
                intent = new Intent(this, ContinueRegistrationActivity.class);
                startActivity(intent);
                intent = null;
                finishAffinity();

                Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show();
            }



        }
    }

    void emailAlreadyExists()
    {
        emailTextField.setError(getString(R.string.email_already_exists));
    }
}