package com.uso.detodo.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uso.detodo.R;

public class EtiquetaViewHolder extends RecyclerView.ViewHolder{
    private TextView Etiqueta;
    public EtiquetaViewHolder(@NonNull View itemView) {
        super(itemView);
        this.Etiqueta= itemView.findViewById(R.id.txvEtiqueta);
    }

    public TextView getEtiqueta() {
        return Etiqueta;
    }

    public void setEtiqueta(TextView etiqueta) {
        Etiqueta = etiqueta;
    }
}
