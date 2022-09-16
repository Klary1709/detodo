package com.uso.detodo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uso.detodo.R;
import com.uso.detodo.models.Horario;
import com.uso.detodo.models.HorarioType;
import com.uso.detodo.viewholders.ContactoViewHolder;
import com.uso.detodo.viewholders.HorarioViewHolder;

import java.util.List;

public class HorarioAdapter extends RecyclerView.Adapter<HorarioViewHolder> {
    private List<Horario> listaHorarios;

    public HorarioAdapter(List<Horario> listaHorarios) { this.listaHorarios = listaHorarios; }

    @NonNull
    @Override
    public HorarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_horario, parent, false);
        return new HorarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorarioViewHolder holder, int position) {
        Horario horario = listaHorarios.get(position);
        holder.getTextviewDiasHorario().setText(horario.getDias());
        holder.getTextviewDesdeHorario().setText(horario.getDesde());
        holder.getTextviewHastaHorario().setText(horario.getHasta());
        if(horario.getTipo() == HorarioType.DELIVERY){
            holder.getImageviewRelojHorario().setVisibility(View.GONE);
            holder.getImageviewMotoHorario().setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() { return listaHorarios.size(); }
}
