package com.uso.detodo.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uso.detodo.R;

public class HorarioViewHolder extends RecyclerView.ViewHolder {
    private TextView textviewDiasHorario;
    private TextView textviewDesdeHorario;
    private TextView textviewHastaHorario;
    private ImageView imageviewRelojHorario;
    private ImageView imageviewMotoHorario;

    public HorarioViewHolder(@NonNull View itemView) {
        super(itemView);
        textviewDiasHorario = itemView.findViewById(R.id.textviewDiasHorario);
        textviewDesdeHorario = itemView.findViewById(R.id.textviewDesdeHorario);
        textviewHastaHorario = itemView.findViewById(R.id.textviewHastaHorario);
        imageviewRelojHorario = itemView.findViewById(R.id.imageviewRelojHorario);
        imageviewMotoHorario = itemView.findViewById(R.id.imageviewMotoHorario);
    }

    public TextView getTextviewDiasHorario() {
        return textviewDiasHorario;
    }

    public TextView getTextviewDesdeHorario() {
        return textviewDesdeHorario;
    }

    public TextView getTextviewHastaHorario() {
        return textviewHastaHorario;
    }

    public ImageView getImageviewRelojHorario() {
        return imageviewRelojHorario;
    }

    public ImageView getImageviewMotoHorario() {
        return imageviewMotoHorario;
    }
}
