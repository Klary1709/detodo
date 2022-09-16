package com.uso.detodo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uso.detodo.adapters.ContactoAdapter;
import com.uso.detodo.adapters.HorarioAdapter;
import com.uso.detodo.models.Contacto;
import com.uso.detodo.models.Horario;

import java.util.List;

public class InfoFragment extends Fragment {
    private List<Horario> listaHorarios;
    private List<Horario> listaHorariosDelivery;
    private String descripcion;
    private List<String> categorias;
    private HorarioAdapter adapter;
    private RecyclerView recyclerHorarios;
    private LinearLayoutManager layoutManager;
    private TextView textviewTituloPerfil;
    private String titulo;

    private TextView labelDeliveryHorarios;
    private RecyclerView recyclerHorariosDelivery;
    private HorarioAdapter adapterDelivery;

    private LinearLayout linearTagsPerfil;

    public InfoFragment(List<Horario> data, String descripcion, String titulo) {
        listaHorarios = data;
        this.descripcion = descripcion;
        this.titulo = titulo;
    }

    public InfoFragment(List<Horario> data, List<Horario> dataDelivery, String descripcion, String titulo) {
        listaHorarios = data;
        this.descripcion = descripcion;
        this.titulo = titulo;
        this.listaHorariosDelivery = dataDelivery;
    }

    public InfoFragment(List<Horario> data, List<Horario> dataDelivery, String descripcion, String titulo, List<String> categorias) {
        listaHorarios = data;
        this.descripcion = descripcion;
        this.titulo = titulo;
        this.listaHorariosDelivery = dataDelivery;
        this.categorias = categorias;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_info, container, false);

        textviewTituloPerfil = view.findViewById(R.id.textviewTituloPerfil);
        textviewTituloPerfil.setText(titulo);

        adapter = new HorarioAdapter(listaHorarios);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerHorarios = view.findViewById(R.id.recyclerHorariosPerfil);
        TextView desc = view.findViewById(R.id.textviewDescripcionPerfil);
        desc.setText(this.descripcion);

        linearTagsPerfil = view.findViewById(R.id.linearTagsPerfil);
        for(String c: categorias){
            TextView tv = new TextView(view.getContext(), null, 0, R.style.TagPerfil);
            tv.setText(c);
            linearTagsPerfil.addView(tv);
        }

        recyclerHorarios.setAdapter(adapter);
        recyclerHorarios.setLayoutManager(layoutManager);
        recyclerHorarios.setHasFixedSize(true);

        labelDeliveryHorarios = view.findViewById(R.id.labelDeliveryHorarios);
        if(listaHorariosDelivery != null){
            adapterDelivery = new HorarioAdapter(listaHorariosDelivery);
            recyclerHorariosDelivery = view.findViewById(R.id.recyclerHorariosDeliveryPerfil);

            recyclerHorariosDelivery.setAdapter(adapterDelivery);
            recyclerHorariosDelivery.setHasFixedSize(true);
        }else{
            labelDeliveryHorarios.setVisibility(View.GONE);
        }

        return view;
    }
}