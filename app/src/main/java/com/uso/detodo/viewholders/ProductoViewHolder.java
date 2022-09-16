package com.uso.detodo.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.uso.detodo.R;
import com.uso.detodo.models.Producto;

public class ProductoViewHolder extends RecyclerView.ViewHolder {
    private CardView cardProducto;
    private ImageView imageviewArrowProducto;
    private ImageView ivProducto;
    private TextView tvTituloProducto;
    private TextView tvPrecioProducto;
    private TextView tvPrecioRebajadoProducto;
    private TextView tvDescripcionProducto;

    public ProductoViewHolder(@NonNull View itemView) {
        super(itemView);
        cardProducto = itemView.findViewById(R.id.cardProducto);
        imageviewArrowProducto = itemView.findViewById(R.id.imageviewArrowProducto);
        ivProducto = itemView.findViewById(R.id.ivProducto);
        tvTituloProducto = itemView.findViewById(R.id.tvTituloProducto);
        tvPrecioProducto = itemView.findViewById(R.id.tvPrecioProducto);
        tvPrecioRebajadoProducto = itemView.findViewById(R.id.tvPrecioRebajadoProducto);
        tvDescripcionProducto = itemView.findViewById(R.id.tvDescripcionProducto);
    }

    public CardView getCardProducto() {
        return cardProducto;
    }

    public ImageView getImageviewArrowProducto() {
        return imageviewArrowProducto;
    }

    public ImageView getIvProducto() {
        return ivProducto;
    }

    public TextView getTvTituloProducto() {
        return tvTituloProducto;
    }

    public TextView getTvPrecioProducto() {
        return tvPrecioProducto;
    }

    public TextView getTvPrecioRebajadoProducto() {
        return tvPrecioRebajadoProducto;
    }

    public TextView getTvDescripcionProducto() {
        return tvDescripcionProducto;
    }
}
