package com.uso.detodo.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.uso.detodo.CategoriasActivity;
import com.uso.detodo.NegociosActivity;
import com.uso.detodo.R;
import com.uso.detodo.models.Categoria;
import com.uso.detodo.viewholders.CategoriaViewHolder;
import com.uso.detodo.viewholders.EtiquetaViewHolder;
import com.uso.detodo.viewholders.NegocioViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Observable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaViewHolder> implements Filterable {
    public static String KEY_TITLE_CATEGORIA = "com.uso.detodo.KEY_TITLE_CATEGORIA";

    private static List<Categoria> listaCatergorias;
    private static List<Categoria> listaAllCatergorias;

    public CategoriaAdapter(List<Categoria> data){
        listaCatergorias = data;
        listaAllCatergorias = new ArrayList(data);
    }


    @NonNull
    @Override
    public CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inicializar la plantilla
        View vista = LayoutInflater.from(parent.getContext()).inflate (R.layout.card_categoria,parent,false);
        CategoriaViewHolder vhCategoria = new CategoriaViewHolder(vista);
        return vhCategoria;
    }


    @Override
    public void onBindViewHolder(@NonNull final CategoriaViewHolder holder, final int i) {
        //Unimos Plantilla con la data
        final List<Categoria> lstCat = listaCatergorias;
        holder.getCategoria().setText(lstCat.get(i).getCategoria());
        holder.getNumNegocios().setText(lstCat.get(i).getNumNegocios() + " Negocios");
        holder.getImgIcono().setImageResource(lstCat.get(i).getIcono());

        CardView card = holder.getCardCategoria();
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent negocio = new Intent(holder.itemView.getContext(),NegociosActivity.class);

                SharedPreferences.Editor editor = holder.itemView.getContext().getSharedPreferences(CategoriasActivity.KEY_FAVORITOS_SHARED, Context.MODE_PRIVATE).edit();
                editor.putBoolean(CategoriasActivity.KEY_IS_FAVORITOS, false);
                editor.putString(KEY_TITLE_CATEGORIA, lstCat.get(i).getCategoria());
                editor.apply();

                ContextCompat.startActivity (holder.itemView.getContext(),negocio,null);
            }
        });
    }

    @Override
    public int getItemCount() {
        //devuleve cuantos items tengo
        return this.listaCatergorias.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(final CharSequence charSequence) {
            List<Categoria> filteredList = new ArrayList<>();

            if(charSequence.toString().isEmpty())
                filteredList = listaAllCatergorias;
            else{
                for(Categoria c: listaAllCatergorias){
                    //si el título o las keywords contienen el texto buscado
                    if(
                            cleanTildes(c.getCategoria().toLowerCase()).contains(cleanTildes(charSequence.toString().toLowerCase()))
                            || cleanTildes(c.getKeywords().toString().toLowerCase()).contains(cleanTildes(charSequence.toString().toLowerCase()))

                    ){
                        filteredList.add(c); //se añade el elemento a la lista
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            listaCatergorias.clear();
            listaCatergorias.addAll((Collection<? extends Categoria>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    private String cleanTildes(String input){
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = input;
        for(int i=0; i<original.length(); i++){
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }
        return output;
    }
}
