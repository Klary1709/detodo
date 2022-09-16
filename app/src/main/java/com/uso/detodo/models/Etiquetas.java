package com.uso.detodo.models;


import android.view.View;

import androidx.annotation.NonNull;

import com.uso.detodo.R;

import java.util.ArrayList;
import java.util.List;

public class Etiquetas {
    private String Etiqueta;
    private static List<Etiquetas> lstEtiquetas = new ArrayList<>();

    public Etiquetas(){}

    public Etiquetas(String etiqueta) {
        Etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return Etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        Etiqueta = etiqueta;
    }

    public void addEtiqueta(Etiquetas etiqueta){
        lstEtiquetas.add(etiqueta);
    }

    public static List<Etiquetas> getLstEtiquetas(){return lstEtiquetas;}

    public static void llenarEtiquetas(){
        Etiquetas e = new Etiquetas();
        e.setEtiqueta("Musica");
        lstEtiquetas.add(e);
    }
}

