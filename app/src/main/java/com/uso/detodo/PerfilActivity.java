package com.uso.detodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.uso.detodo.adapters.NegocioAdapter;
import com.uso.detodo.adapters.SectionsPagerAdapter;
import com.uso.detodo.ContactType;
import com.uso.detodo.models.Contacto;
import com.uso.detodo.models.Horario;
import com.uso.detodo.models.HorarioType;
import com.uso.detodo.models.Oferta;
import com.uso.detodo.models.Producto;
import com.uso.detodo.utils.DownloadImageFromInternet;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import com.google.firebase.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.uso.detodo.CategoriasActivity.KEY_FAVORITOS_SHARED;
import static com.uso.detodo.CategoriasActivity.KEY_NEGOCIOS_IDS;

public class PerfilActivity extends AppCompatActivity {
    ViewPager viewpager;
    TabLayout tabs;
    Toolbar toolbar;
    String idnegocio;
    CollapsingToolbarLayout coltoolPerfil;
    String tituloPerfil;
    FirebaseFirestore db;
    String descripcion = "";
    List<String> categorias = new ArrayList<>();
    ImageView imageviewImagenPerfil;
    List<Contacto> listaContactos = new ArrayList<>();
    List<Producto> listaProductos = new ArrayList<>();
    List<String> listaImagenes = new ArrayList<>();
    List<Oferta> listaOfertas = new ArrayList<>();
    List<Horario> listaHorarios = new ArrayList<>();
    List<Horario> listaHorariosDeliviery = new ArrayList<>();
    FloatingActionButton floatingFavoritosPerfil;
    Set<String> ids;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        db = FirebaseFirestore.getInstance();

        imageviewImagenPerfil = findViewById(R.id.imageviewImagenPerfil);
        coltoolPerfil = findViewById(R.id.coltoolPerfil);
        floatingFavoritosPerfil = findViewById(R.id.floatingFavoritosPerfil);

        Bundle bundle = getIntent().getExtras();
        if(bundle.getString(NegocioAdapter.KEY_TITLE_NEGOCIO) != null) {
            idnegocio = bundle.getString(NegocioAdapter.KEY_ID_NEGOCIO);
            coltoolPerfil.setTitle(bundle.getString(NegocioAdapter.KEY_TITLE_NEGOCIO));
            tituloPerfil = bundle.getString(NegocioAdapter.KEY_TITLE_NEGOCIO);
        }

        SharedPreferences prefs = getSharedPreferences(CategoriasActivity.KEY_FAVORITOS_SHARED, Context.MODE_PRIVATE);
        ids = prefs.getStringSet(CategoriasActivity.KEY_NEGOCIOS_IDS, null);

        initToolbar();

        cargarPerfil();

        floatingFavoritosPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ids.contains(idnegocio)){
                    Toast.makeText(getBaseContext(), tituloPerfil+" se removió de favoritos", Toast.LENGTH_SHORT).show();
                    ids.remove(idnegocio);
                    floatingFavoritosPerfil.setImageDrawable(ResourcesCompat.getDrawable(getBaseContext().getResources(), R.drawable.ic_heart, null));
                }else{
                    Toast.makeText(getBaseContext(), tituloPerfil+" se añadió a favoritos", Toast.LENGTH_SHORT).show();
                    ids.add(idnegocio);
                    floatingFavoritosPerfil.setImageDrawable(ResourcesCompat.getDrawable(getBaseContext().getResources(), R.drawable.ic_heart_pressed, null));
                }

                SharedPreferences.Editor editor = getSharedPreferences(KEY_FAVORITOS_SHARED, Context.MODE_PRIVATE).edit();
                editor.remove(KEY_NEGOCIOS_IDS);
                editor.putStringSet(KEY_NEGOCIOS_IDS, new HashSet<>(ids));
                editor.apply();

                SharedPreferences prefsuser = getSharedPreferences(CategoriasActivity.KEY_USER_SHARED, Context.MODE_PRIVATE);
                uid = prefsuser.getString(LoginActivity.KEY_UID, null);

                Map<String, Object> map = new HashMap<>();
                map.put("negocios", new ArrayList<>(ids));
                db.collection("usuarios").document(uid).set(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("SETDATA","FUNCIONA");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("SETDATA","NO FUNCIONA");
                            }
                        });
            }
        });
    }

    private void initToolbar(){
        toolbar = findViewById(R.id.toolbarPerfil);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void cargarPerfil(){
        db.collection("negocios").document(idnegocio).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    Map<String, Object> map = doc.getData();

                    //obteniendo info
                    new DownloadImageFromInternet(imageviewImagenPerfil).execute(map.get("imagen").toString());
                    descripcion = map.get("descripcion").toString();
                    categorias = (List<String>) map.get("categorias");

                    if(ids.contains(doc.getId()))
                        floatingFavoritosPerfil.setImageDrawable(ResourcesCompat.getDrawable(getBaseContext().getResources(), R.drawable.ic_heart_pressed, null));

                    //obteniendo contactos
                    List<HashMap<String, Object>> mapList = (List<HashMap<String, Object>>) map.get("contactos");
                    for(HashMap<String, Object> contacto: mapList){
                        Contacto c = new Contacto(
                                contacto.get("contacto").toString(),
                                contacto.get("valor").toString(),
                                ContactType.valueOf(contacto.get("tipo").toString())
                        );
                        listaContactos.add(c);
                    }

                    //obteniendo horarios
                    mapList = (List<HashMap<String, Object>>) map.get("horarios");
                    for(HashMap<String, Object> horario: mapList){
                        Horario h;
                        if(horario.get("tipo").toString().equals(HorarioType.DELIVERY.name())){
                            h = new Horario(
                                    horario.get("dias").toString(),
                                    horario.get("hora_apertura").toString(),
                                    horario.get("hora_cierre").toString(),
                                    HorarioType.DELIVERY
                            );
                            listaHorariosDeliviery.add(h);
                        }else{
                            h = new Horario(
                                horario.get("dias").toString(),
                                horario.get("hora_apertura").toString(),
                                horario.get("hora_cierre").toString()
                            );
                            listaHorarios.add(h);
                        }
                    }

                    //obteniendo productos
                    mapList = (List<HashMap<String, Object>>) map.get("productos");
                    if(mapList != null) {
                        for (HashMap<String, Object> producto : mapList) {
                            Producto p = new Producto(
                                    producto.get("nombre").toString(),
                                    producto.get("descripcion").toString(),
                                    Double.parseDouble(producto.get("precio").toString()),
                                    (List<String>) producto.get("imagenes")
                            );
                            listaProductos.add(p);
                        }
                    }

                    //obteniendo ofertas
                    mapList = (List<HashMap<String, Object>>) map.get("ofertas");
                    if(mapList != null){
                        for(HashMap<String, Object> oferta: mapList){
                            Oferta o = new Oferta(
                                    oferta.get("nombre").toString(),
                                    oferta.get("descripcion").toString(),
                                    (Timestamp) oferta.get("desde"),
                                    (Timestamp) oferta.get("hasta"),
                                    oferta.get("imagen").toString()
                            );
                            listaOfertas.add(o);
                        }
                    }

                    //luego de haber cargado la info
                    setViewPager();
                }
            }
        });
    }

    private void setViewPager(){
        viewpager = findViewById(R.id.viewpagerPerfil);
        tabs = (TabLayout) findViewById(R.id.tabsPerfil);

        tabs.setupWithViewPager(viewpager);
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager(), 0);
        adapter.addFragment(new InfoFragment(listaHorarios, listaHorariosDeliviery, descripcion, tituloPerfil, categorias), "Información");
        adapter.addFragment(new ContactosFragment(listaContactos), "Contactos");
        adapter.addFragment(new ProductosFragment(listaProductos), "Productos");
        adapter.addFragment(new OfertasFragment(listaOfertas), "Ofertas");
        viewpager.setAdapter(adapter);
    }
}