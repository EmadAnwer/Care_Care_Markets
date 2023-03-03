package com.example.carecaremarkets;

import static com.example.carecaremarkets.constants.LOGGED_IN;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.carecaremarkets.daos.UsersDAO;
import com.example.carecaremarkets.tables.UserOrders;
import com.example.carecaremarkets.tables.Users;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class ProfileSettingsActivity extends AppCompatActivity implements TextWatcher, View.OnFocusChangeListener {
    TextInputLayout nameTextField, emailTextField, phoneTextField,address2TextField;
    TextInputEditText selectedTextInputField ;
    TextInputLayout addressTextField;
    Spinner governoratesSpinner;
    ShapeableImageView imgProfile;
    boolean isChanged;

    Uri imageUri;
    Button save;
    UsersDAO usersDAO;
    Users user;
    ArrayAdapter<CharSequence> adapter2;
    String governorate,name,phone,address,email;
    int userID;
    SharedPreferences pref;
    byte[] profileImage;

    byte[] userPhotoArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        pref = getSharedPreferences("userData", Context.MODE_PRIVATE);


        //setting ids for views
        nameTextField = findViewById(R.id.nameTextField);
        emailTextField = findViewById(R.id.emailTextField);
        phoneTextField = findViewById(R.id.phoneTextField);

        imgProfile =findViewById(R.id.imgProfileSettings);
        governoratesSpinner =findViewById(R.id.governoratesSpinner4);
        addressTextField = findViewById(R.id.address2TextField);
        save = findViewById(R.id.saveButton);

        //set OnFocusChangeListener
        Objects.requireNonNull(nameTextField.getEditText()).setOnFocusChangeListener(this);
        Objects.requireNonNull(emailTextField.getEditText()).setOnFocusChangeListener(this);
        Objects.requireNonNull(phoneTextField.getEditText()).setOnFocusChangeListener(this);


        //set TextChangedListener
        nameTextField.getEditText().addTextChangedListener(this);
        emailTextField.getEditText().addTextChangedListener(this);
        phoneTextField.getEditText().addTextChangedListener(this);


        governorate = pref.getString("userGovernorate","error");
        name = pref.getString("userName","error");
        email = pref.getString("userEmail","error");
        phone = pref.getString("userPhone","error");
        address = pref.getString("userAddress","error");
        userID = pref.getInt("userID", -1);


        nameTextField.getEditText().setText(name);
        emailTextField.getEditText().setText(email);
        phoneTextField.getEditText().setText(phone);
        Objects.requireNonNull(addressTextField.getEditText()).setText(address);


        usersDAO = CareCareMarketsDatabase.getInstance(this).usersDAO();
        userPhotoArray = usersDAO.getUserPhoto(pref.getInt("userID",-1));
        if(userPhotoArray != null)
        {
            Bitmap bmp= BitmapFactory.decodeByteArray(userPhotoArray, 0 , userPhotoArray.length);
            imgProfile.setImageBitmap(bmp);

        }


        adapter2 = ArrayAdapter.createFromResource(this, R.array.governorates2, android.R.layout.simple_spinner_item);
        governoratesSpinner.setAdapter(adapter2);
        if (governorate != null) {
            int spinnerPosition = adapter2.getPosition(governorate);
            governoratesSpinner.setSelection(spinnerPosition);
        }

    }

    public void back(View view) {super.onBackPressed();}

    public void pickProfileImage(View view) {
        ImagePicker.Companion.with(this)
                .cropSquare()	    			//Crop image(Optional), Check Customization for more option
                .compress(515)			        //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            assert data != null;
            imageUri = data.getData();
            imgProfile.setImageURI(imageUri);
            isChanged = true;
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, getString(R.string.ERROR), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.task_cancelled), Toast.LENGTH_SHORT).show();
        }
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if (selectedTextInputField == null)
            return;



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

    public void save(View view) throws IOException {
        Button v = (Button) view;
        v.setEnabled(false);
        user= new Users();


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



        /*user.first_name =  Objects.requireNonNull(nameTextField.getEditText()).getText().toString();
        user.email = email;
        user.phone = Objects.requireNonNull(phoneTextField.getEditText()).getText().toString();
        user.address = Objects.requireNonNull(addressTextField.getEditText()).getText().toString();
        user.governorate = governoratesSpinner.getSelectedItem().toString();
        user.profile_picture = profileImage;*/
        if(isChanged)
        {
            InputStream iStream =   getContentResolver().openInputStream(imageUri);
            userPhotoArray = getBytes(iStream);
        }



        // user_id, profile_picture, name, phone, address, governorate
        usersDAO = CareCareMarketsDatabase.getInstance(this).usersDAO();
        usersDAO.UpdateAccount(userID,profileImage,Objects.requireNonNull(nameTextField.getEditText()).getText().toString(),
                Objects.requireNonNull(phoneTextField.getEditText()).getText().toString(),
                Objects.requireNonNull(addressTextField.getEditText()).getText().toString(),
                governoratesSpinner.getSelectedItem().toString());

    }


}