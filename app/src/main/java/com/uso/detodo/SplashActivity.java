package com.uso.detodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        navigationChoice();
    }

    private void navigationChoice(){
        SharedPreferences prefs = getSharedPreferences(CategoriasActivity.KEY_USER_SHARED, Context.MODE_PRIVATE);
        String provider = prefs.getString(LoginActivity.KEY_PROVIDER, null);
        Intent intent;

        if(provider == null){
            intent = new Intent(getBaseContext(), LoginActivity.class);
        }else{
            if(provider.equals(Provider.GUEST.name()))
                intent = new Intent(getBaseContext(), LoginActivity.class);
            else
                intent = new Intent(getBaseContext(), CategoriasActivity.class);
        }
        startActivity(intent);
        finish();
    }
}