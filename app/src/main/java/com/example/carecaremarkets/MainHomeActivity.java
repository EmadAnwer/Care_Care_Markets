package com.example.carecaremarkets;

import static com.example.carecaremarkets.constants.LOGGED_IN;
import static com.example.carecaremarkets.constants.LOGGED_OUT;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.carecaremarkets.databinding.ActivityMainHomeBinding;

import java.util.Locale;

public class MainHomeActivity extends AppCompatActivity {
    int updated;
    SharedPreferences pref;
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.carecaremarkets.databinding.ActivityMainHomeBinding binding =ActivityMainHomeBinding.inflate(getLayoutInflater());


        constants.getLANGUAGE(this);
        Locale locale;
        if(constants.LANGUAGE == constants.AR)
        {
            locale = new Locale("ar");

        }
        else
        {
            locale = new Locale("en");
        }
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());
        setContentView(binding.getRoot());
        pref = getSharedPreferences("userData", Context.MODE_PRIVATE);

        if(pref.getInt("userLogin",LOGGED_OUT) == LOGGED_OUT)
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            intent = null;
            finishAffinity();
        }

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_serviceProvider, R.id.navigation_stores)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main2);
        NavigationUI.setupWithNavController(binding.navView, navController);

    }

    @Override
    protected void onResume() {
        pref = getSharedPreferences("userData", Context.MODE_PRIVATE);

        updated = pref.getInt("userUpdate",0);


        super.onResume();



    }
}