package com.uso.detodo.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.uso.detodo.R;

public class OfertaViewHolder extends RecyclerView.ViewHolder {
    private CardView cardOferta;
    private ImageView imageviewArrowOferta;
    private ImageView ivOferta;
    private LinearLayout expandibleOferta;
    private TextView tvTituloOferta;
    private TextView tvDescripcionOferta;
    private TextView tvOfertaDesde;
    private TextView tvOfertaHasta;

    public OfertaViewHolder(@NonNull View itemView) {
        super(itemView);
        cardOferta = itemView.findViewById(R.id.cardOferta);
        ivOferta = itemView.findViewById(R.id.ivOferta);
        imageviewArrowOferta = itemView.findViewById(R.id.imageviewArrowOferta);
        expandibleOferta = itemView.findViewById(R.id.expandibleOferta);
        tvTituloOferta = itemView.findViewById(R.id.tvTituloOferta);
        tvDescripcionOferta = itemView.findViewById(R.id.tvDescripcionOferta);
        tvOfertaDesde = itemView.findViewById(R.id.tvOfertaDesde);
        tvOfertaHasta = itemView.findViewById(R.id.tvOfertaHasta);
    }

    public CardView getCardOferta() {
        return cardOferta;
    }

    public ImageView getIvOferta() {
        return ivOferta;
    }

    public ImageView getImageviewArrowOferta() {
        return imageviewArrowOferta;
    }

    public LinearLayout getExpandibleOferta() {
        return expandibleOferta;
    }

    public TextView getTvTituloOferta() {
        return tvTituloOferta;
    }

    public void setTvTituloOferta(TextView tvTituloOferta) {
        this.tvTituloOferta = tvTituloOferta;
    }

    public TextView getTvDescripcionOferta() {
        return tvDescripcionOferta;
    }

    public void setTvDescripcionOferta(TextView tvDescripcionOferta) {
        this.tvDescripcionOferta = tvDescripcionOferta;
    }

    public TextView getTvOfertaDesde() {
        return tvOfertaDesde;
    }

    public void setTvOfertaDesde(TextView tvOfertaDesde) {
        this.tvOfertaDesde = tvOfertaDesde;
    }

    public TextView getTvOfertaHasta() {
        return tvOfertaHasta;
    }

    public void setTvOfertaHasta(TextView tvOfertaHasta) {
        this.tvOfertaHasta = tvOfertaHasta;
    }
}
