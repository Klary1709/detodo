package com.uso.detodo.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Producto {
    private String nombre;
    private String descripcion;
    private Double precio;
    private Double precio_rebajado;
    private List<String> url_imagenes;

    public Producto(){
        nombre = "";
        descripcion = "";
        precio = 0.0;
        precio_rebajado = 0.0;
        url_imagenes = new ArrayList<>();
    }

    public Producto(String nombre, String descripcion, Double precio, Double precio_rebajado, List<String> url_imagenes) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.precio_rebajado = precio_rebajado;
        this.url_imagenes = url_imagenes;
    }

    public Producto(String nombre, String descripcion, Double precio, List<String> url_imagenes) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.precio_rebajado = 0.0;
        this.url_imagenes = url_imagenes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getPrecio_rebajado() {
        return precio_rebajado;
    }

    public void setPrecio_rebajado(Double precio_rebajado) {
        this.precio_rebajado = precio_rebajado;
    }

    public List<String> getUrl_imagenes() {
        return url_imagenes;
    }

    public void setUrl_imagenes(List<String> url_imagenes) {
        this.url_imagenes = url_imagenes;
    }
}
