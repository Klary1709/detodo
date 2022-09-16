package com.uso.detodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.uso.detodo.adapters.CategoriaAdapter;
import com.uso.detodo.adapters.NegocioAdapter;
import com.uso.detodo.models.Categoria;
import com.uso.detodo.models.Negocio;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class NegociosActivity extends AppCompatActivity {

    private List<Negocio> lst = new ArrayList<>();
    private RecyclerView lstNegocio;
    private NegocioAdapter adapterNegocio;
    private LinearLayoutManager NegocioManager;
    private CardView cview1,cview2;

    private SwipeRefreshLayout swipeNegocios;

    String param = null;
    boolean isFavoritos = false;
    String uid = null;
    Set<String> ids = null;

    FirebaseFirestore db;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_negocios);

        db = FirebaseFirestore.getInstance();

        cargarPreferencias();
        cargarNegocios();

        swipeNegocios = findViewById(R.id.swipeNegocios);
        swipeNegocios.setRefreshing(true);
        swipeNegocios.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                lst = new ArrayList<>();
                cargarNegocios();
                swipeNegocios.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        lst = new ArrayList<>();
        cargarNegocios();
    }

    private void cargarPreferencias(){
        //se obtiene el set de ids favoritos
        SharedPreferences prefs = getSharedPreferences(CategoriasActivity.KEY_FAVORITOS_SHARED, Context.MODE_PRIVATE);
        ids = prefs.getStringSet(CategoriasActivity.KEY_NEGOCIOS_IDS, null);
        isFavoritos = prefs.getBoolean(CategoriasActivity.KEY_IS_FAVORITOS, false);
        param = prefs.getString(CategoriaAdapter.KEY_TITLE_CATEGORIA, null);

        prefs = getSharedPreferences(CategoriasActivity.KEY_USER_SHARED, Context.MODE_PRIVATE);
        uid = prefs.getString(LoginActivity.KEY_UID, null);

        if(isFavoritos && (ids == null || ids.size() == 0)){
            Toast.makeText(getBaseContext(), "La lista de favoritos está vacía", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void cargarNegocios() {
        if(isFavoritos){
            db.collection("negocios").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    getFavoritos(task);
                }
            });
        }else {
            if (param != null && !param.isEmpty()) {
                db.collection("negocios")
                        .whereArrayContains("categorias", param)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                getNegocios(task);
                            }
                        });
            } else {
                db.collection("negocios").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        getNegocios(task);
                    }
                });
            }
        }

    }

    private void getNegocios(Task<QuerySnapshot> task){
        for (QueryDocumentSnapshot document : task.getResult()) {
            List<String> categorias = new ArrayList<>();

            List<String> keywords = new ArrayList<>();
            if(document.get("keywords") != null)
                keywords.addAll((Collection<? extends String>) document.get("keywords"));

            categorias.addAll((Collection<? extends String>) document.get("categorias"));

            boolean isFav = ids.contains(document.getId());
            Negocio n = new Negocio(
                    document.getId(),
                    Integer.parseInt(document.get("id").toString()),
                    document.get("nombre").toString(),
                    document.get("descripcion").toString().substring(0, 45)+"...",
                    document.get("imagen").toString(),
                    ImageView.ScaleType.valueOf(document.get("imagen_scaleType").toString()),
                    categorias,
                    isFav,
                    keywords
            );
            lst.add(n);
        }

        swipeNegocios.setRefreshing(false);

        lstNegocio = findViewById(R.id.lstNegocios);
        NegocioManager = new LinearLayoutManager(getBaseContext());
        adapterNegocio = new NegocioAdapter(lst, uid, ids);

        lstNegocio.setHasFixedSize(true);
        lstNegocio.setLayoutManager(NegocioManager);
        lstNegocio.setAdapter(adapterNegocio);
    }

    private void getFavoritos(Task<QuerySnapshot> task){
        for (DocumentSnapshot document : task.getResult()) {
            if(ids.contains(document.getId())) {
                List<String> categorias = new ArrayList<>();
                List<String> keywords = new ArrayList<>();
                if(document.get("categorias") != null)
                    keywords.addAll((Collection<? extends String>) document.get("categorias"));

                categorias.addAll((Collection<? extends String>) document.get("categorias"));

                Negocio n = new Negocio(
                        document.getId(),
                        Integer.parseInt(document.get("id").toString()),
                        document.get("nombre").toString(),
                        document.get("descripcion").toString().substring(0, 45)+"...",
                        document.get("imagen").toString(),
                        ImageView.ScaleType.valueOf(document.get("imagen_scaleType").toString()),
                        categorias,
                        true,
                        keywords
                );
                lst.add(n);
            }
        }

        swipeNegocios.setRefreshing(false);

        lstNegocio = findViewById(R.id.lstNegocios);
        NegocioManager = new LinearLayoutManager(getBaseContext());
        adapterNegocio = new NegocioAdapter(lst, uid, ids);

        lstNegocio.setHasFixedSize(true);
        lstNegocio.setLayoutManager(NegocioManager);
        lstNegocio.setAdapter(adapterNegocio);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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
                adapterNegocio.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}