package com.uso.detodo.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.uso.detodo.R;
import com.uso.detodo.models.Producto;
import com.uso.detodo.utils.DownloadImageFromInternet;
import com.uso.detodo.viewholders.ContactoViewHolder;
import com.uso.detodo.viewholders.ProductoViewHolder;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoViewHolder> {
    private List<Producto> listaProductos;

    public ProductoAdapter(List<Producto> data){ listaProductos = data; }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_producto, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductoViewHolder holder, int position) {
//        holder.getIvProducto().setImageResource(R.drawable.negocio);
        holder.getTvTituloProducto().setText(listaProductos.get(position).getNombre());
        holder.getTvDescripcionProducto().setText(listaProductos.get(position).getDescripcion());

        ImageView iv = holder.getIvProducto();
        new DownloadImageFromInternet(iv).execute(listaProductos.get(position).getUrl_imagenes().get(0));

        String precio = "", precio_rebajado = "";
        if(listaProductos.get(position).getPrecio_rebajado() > 0.0){
            precio = "$" + listaProductos.get(position).getPrecio() + "";
            precio_rebajado = "$" + listaProductos.get(position).getPrecio_rebajado() + "";
            holder.getTvPrecioProducto().setText(precio_rebajado);
            holder.getTvPrecioRebajadoProducto().setText(precio);
        }else{
            precio = "$" + listaProductos.get(position).getPrecio() + "";
            holder.getTvPrecioProducto().setText(precio);
            holder.getTvPrecioRebajadoProducto().setVisibility(View.GONE);
        }

        final CardView card = holder.getCardProducto();
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.getTvDescripcionProducto().getVisibility() == View.GONE) {
                    TransitionManager.beginDelayedTransition(card, new AutoTransition());
                    holder.getTvDescripcionProducto().setVisibility(View.VISIBLE);
                    holder.getImageviewArrowProducto().setImageResource(R.drawable.ic_arrow_up);
                }
                else {
                    TransitionManager.beginDelayedTransition(card, new AutoTransition());
                    holder.getTvDescripcionProducto().setVisibility(View.GONE);
                    holder.getImageviewArrowProducto().setImageResource(R.drawable.ic_arrow_down);
                }
            }
        });
    }

    @Override
    public int getItemCount() { return listaProductos.size(); }
}
