package com.uso.detodo.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.uso.detodo.R;

public class ContactoViewHolder extends RecyclerView.ViewHolder {
    private CardView cardContacto;
    private ImageView ivContactoIcon;
    private TextView tvContactoContent;

    public ContactoViewHolder(@NonNull View itemView) {
        super(itemView);
        cardContacto = itemView.findViewById(R.id.cardContacto);
        ivContactoIcon = itemView.findViewById(R.id.ivContactoIcon);
        tvContactoContent = itemView.findViewById(R.id.tvContactoContent);
    }

    public CardView getCardContacto() {
        return cardContacto;
    }

    public ImageView getIvContactoIcon() {
        return ivContactoIcon;
    }

    public TextView getTvContactoContent() {
        return tvContactoContent;
    }
}
