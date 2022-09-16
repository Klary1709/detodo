package com.uso.detodo.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.uso.detodo.NegociosActivity;
import com.uso.detodo.PerfilActivity;
import com.uso.detodo.R;
import com.uso.detodo.models.Categoria;
import com.uso.detodo.models.Negocio;
import com.uso.detodo.utils.DownloadImageFromInternet;
import com.uso.detodo.viewholders.NegocioViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static androidx.core.content.ContextCompat.getDrawable;
import static com.uso.detodo.CategoriasActivity.KEY_FAVORITOS_SHARED;
import static com.uso.detodo.CategoriasActivity.KEY_NEGOCIOS_IDS;

public class NegocioAdapter extends RecyclerView.Adapter<NegocioViewHolder> implements Filterable {
    public static String KEY_TITLE_NEGOCIO = "";
    public static String KEY_ID_NEGOCIO = "com.uso.detodo.KEY_ID_NEGOCIO";
    private List<Negocio> listadoNegocios = new ArrayList<>();
    private List<Negocio> listadoAllNegocios = new ArrayList<>();
    private String uid = "";
    private Set<String> favoritos;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public NegocioAdapter(List<Negocio> data, String uid, Set<String> favoritos){
        this.favoritos = favoritos;
        this.uid = uid;
        this.listadoNegocios = data;
        this.listadoAllNegocios = new ArrayList<>(data);
    }
    @NonNull
    @Override
    public NegocioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_negocio,parent,false);
        NegocioViewHolder vhNegocios = new NegocioViewHolder(vista);
        return vhNegocios;
    }

    @Override
    public void onBindViewHolder(@NonNull final NegocioViewHolder holder, final int i) {
        final List<Negocio> lstNegocio = this.listadoNegocios;
        holder.getNomNegocio().setText(lstNegocio.get(i).getNomNegocio());

        if(lstNegocio.get(i).getIsFav())
            holder.getBtnFavoritos().setBackground(getDrawable(holder.itemView.getContext(),R.drawable.ic_heart_pressed));
        else
            holder.getBtnFavoritos().setBackground(getDrawable(holder.itemView.getContext(),R.drawable.ic_heart));

        holder.getDireccion().setText(lstNegocio.get(i).getDireccion());
        //holder.getImagenNegocio().setImageResource(lstNegocio.get(i).getImagenNegocio());

        ImageView iv = holder.getImagenNegocio();
        iv.setScaleType(lstNegocio.get(i).getScaleType());
        new DownloadImageFromInternet(iv).execute(lstNegocio.get(i).getImageUrl());

        CardView card = holder.getCardNegocio();
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent perfil = new Intent(holder.itemView.getContext(), PerfilActivity.class);
                perfil.putExtra(KEY_TITLE_NEGOCIO, holder.getNomNegocio().getText().toString());
                perfil.putExtra(KEY_ID_NEGOCIO, lstNegocio.get(i).getId());
                ContextCompat.startActivity(holder.itemView.getContext(),perfil,null);
            }
        });

        final ImageButton setFav = holder.getBtnFavoritos();
        final boolean isFav = lstNegocio.get(i).getIsFav();
        final String negocioId = lstNegocio.get(i).getId();
        setFav.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override

            public void onClick(View view) {
                List<String> favs = new ArrayList<>(favoritos);
                if(favs.contains(negocioId)) {
                    Toast.makeText(holder.itemView.getContext(), lstNegocio.get(i).getNomNegocio()+" se removió de favoritos", Toast.LENGTH_SHORT).show();
                    favs.remove(negocioId);
                    setFav.setBackground(getDrawable(holder.itemView.getContext(),R.drawable.ic_heart));
                }
                else {
                    Toast.makeText(holder.itemView.getContext(), lstNegocio.get(i).getNomNegocio()+" se añadió a favoritos", Toast.LENGTH_SHORT).show();
                    favs.add(negocioId);
                    setFav.setBackground(getDrawable(holder.itemView.getContext(),R.drawable.ic_heart_pressed));
                }

                //se actualizan los favoritos almacenados
                favoritos = new HashSet<>(favs);
                SharedPreferences.Editor editor = holder.itemView.getContext().getSharedPreferences(KEY_FAVORITOS_SHARED, Context.MODE_PRIVATE).edit();
                editor.remove(KEY_NEGOCIOS_IDS);
                editor.putStringSet(KEY_NEGOCIOS_IDS, new HashSet<>(favs));
                editor.apply();

                Map<String, Object> map = new HashMap<>();
                map.put("negocios", favs);
                System.out.println(uid);
                db.collection("usuarios").document(uid).set(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("SETDATA","FUNCIONA");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("SETDATA","NO FUNCIONA");
                            }
                        });
            }
        });

        LinearLayout lay = holder.getLinearlayoutCategorias();
        for(String cat: lstNegocio.get(i).getLstCategorias()){
            TextView tv = new TextView(holder.itemView.getContext(), null, 0, R.style.Tag);
            tv.setText(cat);
            lay.addView(tv);
        }
    }

    @Override
    public int getItemCount() {
        return this.listadoNegocios.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(final CharSequence charSequence) {
            List<Negocio> filteredList = new ArrayList<>();

            if(charSequence.toString().isEmpty())
                filteredList = listadoAllNegocios;
            else{
                for(Negocio n: listadoAllNegocios){
                    //si el título o las keywords contienen el texto buscado
                    boolean doesContain = false;
                    for(String k: n.getKeywords()){
                        if( k.contains(cleanTildes(charSequence.toString().toLowerCase()))){ doesContain = true; break; }
                    }
                    if(
                            cleanTildes(n.getNomNegocio().toLowerCase()).contains(cleanTildes(charSequence.toString().toLowerCase()))
                            || doesContain
                    ){
                        filteredList.add(n); //se añade el elemento a la lista
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            listadoNegocios.clear();
            listadoNegocios.addAll((Collection<? extends Negocio>) filterResults.values);
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
