package com.uso.detodo.models;


import com.uso.detodo.R;

import java.util.ArrayList;
import java.util.List;

public class Categoria {
    private int idCategoria;
    private String Categoria;
    private int NumNegocios;
    private int icono;
    private List<String> keywords;

    public Categoria(){
        this.idCategoria = 0;
        this.Categoria = "";
        this.NumNegocios = 0;
        this.icono = 0;
    }

    public Categoria(int idCategoria, String categoria, int numNegocios, int icono) {
        this.idCategoria = idCategoria;
        Categoria = categoria;
        NumNegocios = numNegocios;
        this.icono = icono;
    }

    public Categoria(String categoria, int numNegocios, int icono, List<String> keywords) {
        Categoria = categoria;
        NumNegocios = numNegocios;
        this.icono = icono;
        this.keywords = keywords;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public int getNumNegocios() {
        return NumNegocios;
    }

    public void setNumNegocios(int numNegocios) {
        NumNegocios = numNegocios;
    }

    public int getIcono() {
        return icono;
    }

    public void setIcono(int icono) {
        this.icono = icono;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}