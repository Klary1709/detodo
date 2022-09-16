package com.uso.detodo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uso.detodo.adapters.ContactoAdapter;
import com.uso.detodo.adapters.ProductoAdapter;
import com.uso.detodo.models.Producto;

import java.util.List;

public class ProductosFragment extends Fragment {
    private List<Producto> listaProductos;
    private ProductoAdapter adapter;
    private RecyclerView recyclerProductos;
    private LinearLayoutManager layoutManager;

    public ProductosFragment(List<Producto> data) {
        listaProductos = data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_productos, container, false);

        adapter = new ProductoAdapter(listaProductos);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerProductos = view.findViewById(R.id.recyclerProductos);

        recyclerProductos.setAdapter(adapter);
        recyclerProductos.setLayoutManager(layoutManager);
        recyclerProductos.setHasFixedSize(true);

        return view;
    }
}