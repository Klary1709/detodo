package com.uso.detodo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uso.detodo.adapters.ContactoAdapter;
import com.uso.detodo.adapters.OfertaAdapter;
import com.uso.detodo.models.Contacto;
import com.uso.detodo.models.Oferta;

import java.util.List;

public class OfertasFragment extends Fragment {
    private List<Oferta> listaOfertas;
    private OfertaAdapter adapter;
    private RecyclerView recyclerOfertas;
    private LinearLayoutManager layoutManager;

    public OfertasFragment(List<Oferta> data) { listaOfertas = data; }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_ofertas, container, false);

        adapter = new OfertaAdapter(listaOfertas);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerOfertas = view.findViewById(R.id.recyclerOfertas);

        recyclerOfertas.setAdapter(adapter);
        recyclerOfertas.setLayoutManager(layoutManager);
        recyclerOfertas.setHasFixedSize(true);

        return view;
    }
}