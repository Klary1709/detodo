package com.uso.detodo.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.uso.detodo.ContactType;
import com.uso.detodo.R;
import com.uso.detodo.models.Contacto;
import com.uso.detodo.viewholders.ContactoViewHolder;

import java.util.List;

public class ContactoAdapter extends RecyclerView.Adapter<ContactoViewHolder> {
    private List<Contacto> listaContactos;
    private Context context;

    public ContactoAdapter(List<Contacto> listaContactos) {
        this.listaContactos = listaContactos;
    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_contacto, parent, false);
        return new ContactoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, final int position) {
        holder.getIvContactoIcon().setImageResource(getIcon(position));
        holder.getTvContactoContent().setText(listaContactos.get(position).getContacto());

        CardView card = holder.getCardContacto();
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent(position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaContactos.size();
    }

    private int getIcon(int position){
        Contacto contacto = listaContactos.get(position);
        int icon;
        switch (contacto.getTipo()) {
            case FACEBOOK: icon = R.drawable.ic_facebook;
                break;
            case WHATSAPP: icon = R.drawable.ic_whatsapp;
                break;
            case INSTAGRAM: icon = R.drawable.ic_instagram;
                break;
            case PHONE: icon = R.drawable.ic_icon_circle_phone;
                break;
            case TELEGRAM: icon = R.drawable.ic_telegram;
                break;
            default: icon = 2131165365;
            break;
        }
        return icon;
    }

    private Intent getIntent(int position) {
        Intent intent = new Intent();
        Uri uri;
        Contacto contacto = listaContactos.get(position);

        try {
            switch (contacto.getTipo()) {
                case PHONE:
                    uri = Uri.parse("tel:" + contacto.getValor().replace(" ", ""));
                    intent.setAction(Intent.ACTION_DIAL);
                    intent.setData(uri);
                    break;
                case EMAIL:
                    intent.setAction(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:"));
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"jon@example.com"}); // recipients
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Email subject");
                    intent.putExtra(Intent.EXTRA_TEXT, "Email message text");
                    break;
                case FACEBOOK:
                    if (context.getPackageManager().getPackageInfo("com.facebook.katana", 0) != null){
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("fb://facewebmodal/f?href="+contacto.getValor()));
                    }
                    break;
                case INSTAGRAM:
                    if (context.getPackageManager().getPackageInfo("com.instagram.android", 0) != null){
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("http://instagram.com/_u/"+contacto.getValor()));
                    }
                    break;
                case TWITTER:
                    if (context.getPackageManager().getPackageInfo("com.twitter.android", 0) != null){
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("twitter://user?screen_name="+contacto.getValor()));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                    break;
                case TELEGRAM:
                    break;
                case YOUTUBE:
                    break;
                case MAPS:
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("geo:"+contacto.getValor()+"?z=21"));
                    break;
                case WEBSITE:
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(contacto.getValor()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    break;
                case WHATSAPP:
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://api.whatsapp.com/send?phone="+contacto.getValor()));
                    break;
            }
        }catch (PackageManager.NameNotFoundException nnfex){
            switch (contacto.getTipo()){
                case FACEBOOK:
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(contacto.getValor()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    break;
                case INSTAGRAM:
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://instagram.com/"+contacto.getValor()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    break;
                case TWITTER:
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://mobile.twitter.com/"+contacto.getValor()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                break;
            }
        }

        return intent;
    }
}
