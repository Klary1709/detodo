package com.uso.detodo.viewholders;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.uso.detodo.R;
import com.uso.detodo.models.Negocio;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

public class NegocioViewHolder extends RecyclerView.ViewHolder{
    private CardView cardNegocio;
    private TextView NomNegocio;
    private TextView Direccion;
    private ImageView ImagenNegocio;
    private ListView ListaCategorias;
    private ImageButton btnFavoritos;
    private LinearLayout linearlayoutCategorias;

    public NegocioViewHolder(@NonNull final View itemView) {
        super(itemView);
        this.cardNegocio = itemView.findViewById(R.id.cardNegocio);
        this.btnFavoritos = itemView.findViewById(R.id.btnFavoritos);
        this.NomNegocio = itemView.findViewById(R.id.txvNombre);
        this.Direccion = itemView.findViewById(R.id.txvDireccion);
        this.ImagenNegocio = itemView.findViewById(R.id.imgNegocio);
        //this.ListaCategorias = itemView.findViewById(R.id.lts_Categorias);
        this.linearlayoutCategorias = itemView.findViewById(R.id.linearlayoutCategorias);
    }

    public CardView getCardNegocio() {
        return cardNegocio;
    }

    public TextView getNomNegocio() {
        return NomNegocio;
    }

    public void setNomNegocio(TextView nomNegocio) {
        NomNegocio = nomNegocio;
    }

    public TextView getDireccion() {
        return Direccion;
    }

    public void setDireccion(TextView descripcion) {
        Direccion = descripcion;
    }

    public ImageView getImagenNegocio() {
        return ImagenNegocio;
    }

    public void setImagenNegocio(ImageView imagenNegocio) {
        ImagenNegocio = imagenNegocio;
    }

    public ListView getListaCategorias() {
        return ListaCategorias;
    }

    public void setListaCategorias(ListView listaCategorias) {
        ListaCategorias = listaCategorias;
    }

    public ImageButton getBtnFavoritos() {
        return btnFavoritos;
    }

    public void setBtnFavoritos(ImageButton btnFavoritos) {
        this.btnFavoritos = btnFavoritos;
    }

    public LinearLayout getLinearlayoutCategorias() {
        return linearlayoutCategorias;
    }
}
