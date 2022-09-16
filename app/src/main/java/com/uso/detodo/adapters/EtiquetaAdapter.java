package com.uso.detodo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uso.detodo.models.Etiquetas;
import com.uso.detodo.R;
import com.uso.detodo.models.Negocio;
import com.uso.detodo.viewholders.EtiquetaViewHolder;

import java.util.List;

public class EtiquetaAdapter extends RecyclerView.Adapter<EtiquetaViewHolder> {
    private List<Etiquetas> listaEtiquetas;

    public EtiquetaAdapter(List<Etiquetas> data){
        this.listaEtiquetas = data;
    }
    @NonNull
    @Override
    public EtiquetaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_etiqueta,parent,false);
        EtiquetaViewHolder vhEtiqueta = new EtiquetaViewHolder(vista);
        return vhEtiqueta;
    }

    @Override
    public void onBindViewHolder(@NonNull EtiquetaViewHolder holder, int i) {
        List<Etiquetas> lstEtiquetas = this.listaEtiquetas;
        holder.getEtiqueta().setText(lstEtiquetas.get(i).getEtiqueta());
    }

    @Override
    public int getItemCount() {
        return this.listaEtiquetas.size();
    }
}
