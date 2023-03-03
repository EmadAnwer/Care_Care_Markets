package com.example.carecaremarkets;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.carecaremarkets.daos.UsersDAO;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ContinueRegistrationActivity extends AppCompatActivity {
    SharedPreferences pref;
    ShapeableImageView imgProfile;
    int userID;
    UsersDAO usersDAO;
    boolean hasImage;
    Uri imageUri;
    byte[] noPic ;
    TextInputLayout addressTextField;
    Spinner governoratesSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue_registration);

        imgProfile =findViewById(R.id.imgProfileSettings);
        governoratesSpinner =findViewById(R.id.governoratesSpinner2);
        addressTextField = findViewById(R.id.address2TextField);

        pref = getSharedPreferences("userData", Context.MODE_PRIVATE);
        userID =  pref.getInt("userID", -1);
        Toast.makeText(this, "User id = "+userID, Toast.LENGTH_LONG).show();


    }

    public void complete(View view) throws IOException {
        Button b = (Button) view;
        b.setEnabled(false);
        Intent intent = new Intent(this, MainHomeActivity.class);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("userAddress", addressTextField.getEditText().getText().toString());
        editor.putString("userGovernorate", governoratesSpinner.getSelectedItem().toString());
        editor.apply();
        if(hasImage)
        {
            InputStream iStream =   getContentResolver().openInputStream(imageUri);
            byte[] profileImage = getBytes(iStream);
            //check Mail
            usersDAO = CareCareMarketsDatabase.getInstance(this).usersDAO();
            usersDAO.updateProfilePic(profileImage,userID);
            /*intent.putExtra("profile_picture", profileImage);*/


            Toast.makeText(this, "Image Done "+userID, Toast.LENGTH_LONG).show();

        }

        //TODO
        //intent.putExtra("profile_picture", noPic);
        startActivity(intent);
        intent = null;
        finishAffinity();
    }

    public void skip(View view) {

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        intent = null;
        finishAffinity();
    }


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
            hasImage = true;
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
}