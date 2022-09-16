package com.uso.detodo.adapters;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.uso.detodo.R;
import com.uso.detodo.models.Oferta;
import com.uso.detodo.utils.DownloadImageFromInternet;
import com.uso.detodo.viewholders.OfertaViewHolder;
import com.uso.detodo.viewholders.ProductoViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OfertaAdapter extends RecyclerView.Adapter<OfertaViewHolder> {
    List<Oferta> listaOfertas = new ArrayList<>();

    public OfertaAdapter(List<Oferta> listaOfertas) { this.listaOfertas = listaOfertas; }

    @NonNull
    @Override
    public OfertaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_oferta, parent, false);
        return new OfertaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OfertaViewHolder holder, int position) {
        holder.getTvTituloOferta().setText(listaOfertas.get(position).getTitulo());
        holder.getTvDescripcionOferta().setText(listaOfertas.get(position).getDescripcion());
        holder.getTvOfertaDesde().setText(new SimpleDateFormat("E, dd/MMM/yy  h:mm a").format(listaOfertas.get(position).getDesdeT().toDate()));
        holder.getTvOfertaHasta().setText(new SimpleDateFormat("E, dd/MMM/yy  h:mm a").format(listaOfertas.get(position).getHastaT().toDate()));

        ImageView iv = holder.getIvOferta();
        new DownloadImageFromInternet(iv).execute(listaOfertas.get(position).getUrl_imagen());

        final CardView card = holder.getCardOferta();
        final LinearLayout expandibleOferta = holder.getExpandibleOferta();
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expandibleOferta.getVisibility() == View.GONE){
                    TransitionManager.beginDelayedTransition(card, new AutoTransition());
                    expandibleOferta.setVisibility(View.VISIBLE);
                    holder.getImageviewArrowOferta().setImageResource(R.drawable.ic_arrow_up);
                }else{
                    TransitionManager.beginDelayedTransition(card, new AutoTransition());
                    expandibleOferta.setVisibility(View.GONE);
                    holder.getImageviewArrowOferta().setImageResource(R.drawable.ic_arrow_down);
                }
            }
        });
    }

    @Override
    public int getItemCount() { return listaOfertas.size(); }
}
