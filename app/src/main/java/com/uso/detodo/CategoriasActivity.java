package com.uso.detodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.uso.detodo.adapters.CategoriaAdapter;
import com.uso.detodo.models.Categoria;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.uso.detodo.adapters.CategoriaAdapter.KEY_TITLE_CATEGORIA;

public class CategoriasActivity extends AppCompatActivity {
    public static final String KEY_USER_SHARED = "com.uso.detodo.USER_PREFS";
    public static final String KEY_FAVORITOS_SHARED = "com.uso.detodo.FAVORITOS_PREFS";
    public static final String KEY_NEGOCIOS_IDS = "com.uso.detodo.KEY_CATEGORIAS_IDS";
    public static final String KEY_ENDED_SESSION = "com.uso.detodo.ENDED_SESSION";
    public static final String KEY_IS_FAVORITOS = "com.uso.detodo.KEY_IS_FAVORITOS";
    private static final String TAG = "com.uso.detodo.LOGGER_KEY";

    private String provider;
    private String email;
    private String username;
    private String image_url;
    private String uid;
    private List<Categoria> lstC = new ArrayList<>();
    private RecyclerView lstCat;
    private CategoriaAdapter adapterCat;
    private LinearLayoutManager CatManager;

    private DrawerLayout drawer;
    private NavigationView navView;
    private ActionBarDrawerToggle toggle;
    private ConstraintLayout navHeader;
    private ImageView imageviewUserImage;
    private TextView textviewUsername;

    private SwipeRefreshLayout swipeCategorias;

    private int closeAppCounter;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);
        setTitle("Categorías");
        closeAppCounter = 0;

        //db instance
        db = FirebaseFirestore.getInstance();

        saveUserData();

        cargarCategorias();
        getFavoritos();

        setNavDrawer();
        setNavHeader();

        swipeCategorias = findViewById(R.id.swipeCategorias);
        swipeCategorias.setRefreshing(true);
        swipeCategorias.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                lstC = new ArrayList<>();
                cargarCategorias();
                swipeCategorias.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onRestart() {
        closeAppCounter = 0;
        super.onRestart();

        //limpio datos de favoritos
        SharedPreferences.Editor prefs = getSharedPreferences(KEY_FAVORITOS_SHARED, Context.MODE_PRIVATE).edit();
        prefs.clear();
        prefs.apply();

        getFavoritos();
        System.out.println("RESTART");
    }

    private void cargarCategorias() {
        db.collection("categorias")
                .whereGreaterThanOrEqualTo("numNegocios", 1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Class res = R.drawable.class;
                    Field field;
                    int drawableId = 0;
                    try {
                        field = res.getField(document.get("icono").toString());
                        drawableId = field.getInt(null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    lstC.add(
                        new Categoria(
                            document.get("categoria").toString(),
                            Integer.parseInt(document.get("numNegocios").toString()),
                            drawableId,
                            (List<String>)document.get("keywords")
                        )
                    );
                }

                swipeCategorias.setRefreshing(false);

                lstCat = findViewById(R.id.lstCategorias);
                CatManager = new LinearLayoutManager(getBaseContext());
                adapterCat = new CategoriaAdapter(lstC);

                lstCat.setHasFixedSize(true);
                lstCat.setLayoutManager(CatManager);
                lstCat.setAdapter(adapterCat);
            }
        });
    }

    @Override
    public void onBackPressed() {
        closeApp();
    }

    private void signOut(){
        if(provider.equals(Provider.FACEBOOK.name())) LoginManager.getInstance().logOut();
        FirebaseAuth.getInstance().signOut();
        //Toast.makeText(getBaseContext(), getString(R.string.success_signout), Toast.LENGTH_SHORT).show();
        wipeUserData();
        wipeFavoritos();
    }

    private void setNavDrawer(){
        drawer = findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navView = findViewById(R.id.navView);
        setupNavigationContent(navView);
    }

    private void setNavHeader(){
        navHeader = (ConstraintLayout) navView.getHeaderView(0);
        imageviewUserImage = navHeader.findViewById(R.id.imageviewUserImage);
        textviewUsername = navHeader.findViewById(R.id.textviewUsername);

        if(provider != null) {
            if (!provider.equals(Provider.GUEST.name())) {
                if (!image_url.isEmpty())
                    new DownloadImageFromInternet(imageviewUserImage).execute(image_url);
                textviewUsername.setText(username);
            } else textviewUsername.setText(getResources().getString(R.string.usuario_invitado));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)) return true;
        switch (item.getItemId()) {
            case android.R.id.home:
                /*signOut();
                finish();*/
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupNavigationContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menuitemCerrarSesion: {
                        signOut();

                        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                        intent.putExtra(KEY_ENDED_SESSION, getBaseContext().getString(R.string.success_signout));
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                    }break;
                    case R.id.menuitemFavoritos:{
                        openFavoritos();
                    }break;
                }
                return false;
            }
        });
    }

    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;
        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView=imageView;
            //Toast.makeText(getApplicationContext(), "Please wait, it may take a few minute...", Toast.LENGTH_SHORT).show();
        }
        protected Bitmap doInBackground(String... urls) {
            String imageURL=urls[0];
            Bitmap bimage=null;
            try {
                InputStream in=new java.net.URL(imageURL).openStream();
                bimage= BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }

    private void saveUserData(){
        SharedPreferences prefs = getSharedPreferences(CategoriasActivity.KEY_USER_SHARED, Context.MODE_PRIVATE);
        String providerSaved = prefs.getString(LoginActivity.KEY_PROVIDER, null);
        if(providerSaved == null) {
            Bundle bundle = getIntent().getExtras();
            provider = bundle.getString(LoginActivity.KEY_PROVIDER);
            email = bundle.getString(LoginActivity.KEY_EMAIL);
            username = bundle.getString(LoginActivity.KEY_USERNAME);
            image_url = bundle.getString(LoginActivity.KEY_IMAGE_URL);
            uid = bundle.getString(LoginActivity.KEY_UID);

            wipeUserData();
            SharedPreferences.Editor preferences = getSharedPreferences(KEY_USER_SHARED, Context.MODE_PRIVATE).edit();
            preferences.putString(LoginActivity.KEY_PROVIDER, provider);
            preferences.putString(LoginActivity.KEY_EMAIL, email);
            preferences.putString(LoginActivity.KEY_USERNAME, username);
            preferences.putString(LoginActivity.KEY_IMAGE_URL, image_url);
            preferences.putString(LoginActivity.KEY_UID, uid);
            preferences.apply();
        }else{
            provider = providerSaved;
            email = prefs.getString(LoginActivity.KEY_EMAIL, null);
            username = prefs.getString(LoginActivity.KEY_USERNAME, null);
            image_url = prefs.getString(LoginActivity.KEY_IMAGE_URL, null);
            uid = prefs.getString(LoginActivity.KEY_UID, null);
        }
    }

    private void wipeUserData(){
        SharedPreferences.Editor preferences = getSharedPreferences(KEY_USER_SHARED, Context.MODE_PRIVATE).edit();
        preferences.clear();
        preferences.apply();
    }

    private void wipeFavoritos(){
        SharedPreferences.Editor preferences = getSharedPreferences(KEY_USER_SHARED, Context.MODE_PRIVATE).edit();
        preferences.clear();
        preferences.apply();
    }

    private void closeApp(){
        closeAppCounter++;
        if(closeAppCounter == 2) {
            if(provider.equals(Provider.GUEST.name())){
                wipeUserData();
            }
            this.finishAffinity();
        }
        else Toast.makeText(getBaseContext(), "Presione otra vez para salir", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.action_buscar);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterCat.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void getFavoritos(){
        SharedPreferences prefs = getSharedPreferences(KEY_USER_SHARED, Context.MODE_PRIVATE);
        String uid = prefs.getString(LoginActivity.KEY_UID, null);

        if(uid != null) {
            db.collection("usuarios")
                    .document(uid)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot doc = task.getResult();
                            ArrayList<String> ids = new ArrayList<>();
                            if(doc.get("negocios") != null)
                                ids.addAll((Collection<? extends String>) doc.get("negocios"));

                            Set<String> set = new HashSet<>(ids);

                            SharedPreferences.Editor editor = getSharedPreferences(KEY_FAVORITOS_SHARED, Context.MODE_PRIVATE).edit();
                            editor.putStringSet(KEY_NEGOCIOS_IDS, set);
                            editor.apply();
                        }
                    });
        }
    }

    private void openFavoritos(){
        Intent intent = new Intent(getBaseContext(), NegociosActivity.class);

        SharedPreferences.Editor editor = getSharedPreferences(CategoriasActivity.KEY_FAVORITOS_SHARED, Context.MODE_PRIVATE).edit();
        editor.putBoolean(CategoriasActivity.KEY_IS_FAVORITOS, true);
        editor.apply();

        startActivity(intent);
    }
}