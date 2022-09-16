package com.uso.detodo.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.uso.detodo.R;

public class CategoriaViewHolder extends RecyclerView.ViewHolder {
    private CardView cardCategoria;
    private TextView Categoria;
    private TextView NumNegocios;
    private ImageView imgIcono;
    public CategoriaViewHolder(@NonNull View itemView) {
        super(itemView);
        this.cardCategoria = itemView.findViewById(R.id.cardCategoria);
        this.Categoria = itemView.findViewById(R.id.txvCategoria);
        this.NumNegocios = itemView.findViewById(R.id.txvNumNegocios);
        this.imgIcono = itemView.findViewById(R.id.imgIcono);
    }

    public CardView getCardCategoria() {
        return cardCategoria;
    }

    public TextView getCategoria() {
        return Categoria;
    }

    public void setCategoria(TextView categoria) {
        Categoria = categoria;
    }

    public TextView getNumNegocios() {
        return NumNegocios;
    }

    public void setNumNegocios(TextView numNegocios) {
        NumNegocios = numNegocios;
    }

    public ImageView getImgIcono() {
        return imgIcono;
    }

    public void setImgIcono(ImageView imgIcono) {
        this.imgIcono = imgIcono;
    }
}
