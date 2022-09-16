package com.uso.detodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.uso.detodo.adapters.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();
        provider = bundle.getString("provider");
    }

    public void abrirCategorias(View view){
        Intent intent = new Intent(this, CategoriasActivity.class);
        startActivity(intent);
    }

    public void abrirNegocios(View view){
        Intent intent = new Intent(this, NegociosActivity.class);
        startActivity(intent);
    }

    public void abrirPerfil(View view){
        Intent intent = new Intent(this, PerfilActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        signOut(); //AUXILIAR (irá en menú deslizable)
        super.onBackPressed();
    }

    private void signOut(){
        if(provider.equals(Provider.FACEBOOK.name()))
            LoginManager.getInstance().logOut();

        FirebaseAuth.getInstance().signOut();
        Toast.makeText(getBaseContext(), getString(R.string.success_signout), Toast.LENGTH_SHORT).show();
    }
}