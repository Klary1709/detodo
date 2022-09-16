package com.uso.detodo.models;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.widget.Button;
import android.widget.ImageView;

import com.uso.detodo.R;

import java.util.ArrayList;
import java.util.List;

public class Negocio {
    private String id;
    private  int idNegocio;
    private String NomNegocio;
    private String Direccion;
    private int ImagenNegocio;
    private String imageUrl;
    private List<Etiquetas> ListaCategorias;
    private List<String> lstCategorias;
    private int btnFavoritos;
    private boolean isFav;
    private ImageView.ScaleType scaleType;
    private List<String> keywords;

    private static List<Negocio> lstNegocios= new ArrayList<>();

    public Negocio(){}

    public Negocio(int idNegocio, String nomNegocio, String direccion, int imagenNegocio) {
        this.idNegocio = idNegocio;
        NomNegocio = nomNegocio;
        Direccion = direccion;
        ImagenNegocio = imagenNegocio;
        this.btnFavoritos = R.drawable.ic_heart_press;
    }

    public Negocio(String id, int idNegocio, String nomNegocio, String direccion, String imageUrl, ImageView.ScaleType scaleType, List<String> categorias) {
        this.id = id;
        this.idNegocio = idNegocio;
        NomNegocio = nomNegocio;
        Direccion = direccion;
        this.imageUrl = imageUrl;
        this.scaleType = scaleType;
        this.btnFavoritos = R.drawable.ic_heart_press;
        this.lstCategorias = categorias;
    }

    public Negocio(String id, int idNegocio, String nomNegocio, String direccion, String imageUrl, ImageView.ScaleType scaleType, List<String> categorias, boolean isFav, List<String> keywords) {
        this.id = id;
        this.idNegocio = idNegocio;
        NomNegocio = nomNegocio;
        Direccion = direccion;
        this.imageUrl = imageUrl;
        this.scaleType = scaleType;
        this.btnFavoritos = R.drawable.ic_heart_press;
        this.lstCategorias = categorias;
        this.isFav = isFav;
        this.keywords = keywords;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public boolean getIsFav() {
        return isFav;
    }

    public void setIsFav(boolean fav) {
        isFav = fav;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ImageView.ScaleType getScaleType() {
        return scaleType;
    }

    public void setScaleType(ImageView.ScaleType scaleType) {
        this.scaleType = scaleType;
    }

    public String getNomNegocio() {
        return NomNegocio;
    }

    public void setNomNegocio(String nomNegocio) {
        NomNegocio = nomNegocio;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public int getImagenNegocio() {
        return ImagenNegocio;
    }

    public void setImagenNegocio(int imagenNegocio) {
        ImagenNegocio = imagenNegocio;
    }

    public List<Etiquetas> getListaCategorias() {
        return ListaCategorias;
    }

    public void setListaCategorias(List<Etiquetas> listaCategorias) {
        ListaCategorias = listaCategorias;
    }

    public int getBtnFavoritos() {
        return btnFavoritos;
    }

    public void setBtnFavoritos() {
        this.btnFavoritos = R.drawable.ic_heart_press;
    }

    public List<String> getLstCategorias() {
        return lstCategorias;
    }

    public void setLstCategorias(List<String> lstCategorias) {
        this.lstCategorias = lstCategorias;
    }
}
