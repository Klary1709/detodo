package com.uso.detodo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uso.detodo.adapters.ContactoAdapter;
import com.uso.detodo.models.Contacto;

import java.util.List;

public class ContactosFragment extends Fragment {
    private List<Contacto> listaContactos;
    private ContactoAdapter adapter;
    private RecyclerView recyclerContactos;
    private LinearLayoutManager layoutManager;

    public ContactosFragment(List<Contacto> data){
        this.listaContactos = data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_contactos, container, false);

        adapter = new ContactoAdapter(listaContactos);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerContactos = view.findViewById(R.id.recyclerContactos);

        recyclerContactos.setAdapter(adapter);
        recyclerContactos.setLayoutManager(layoutManager);
        recyclerContactos.setHasFixedSize(true);

        return view;
    }
}