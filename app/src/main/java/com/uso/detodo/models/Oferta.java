package com.uso.detodo.models;

import java.util.Date;
import com.google.firebase.Timestamp;

public class Oferta {
    private String titulo;
    private String descripcion;
    private Date desde;
    private Date hasta;
    private Timestamp desdeT;
    private Timestamp hastaT;
    private String url_imagen;

    public Oferta(String titulo, String descripcion, Date desde, Date hasta, String url_imagen) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.desde = desde;
        this.hasta = hasta;
        this.url_imagen = url_imagen;
    }

    public Oferta(String titulo, String descripcion, Timestamp desde, Timestamp hasta, String url_imagen) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.desdeT = desde;
        this.hastaT = hasta;
        this.url_imagen = url_imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public Timestamp getDesdeT() {
        return desdeT;
    }

    public void setDesdeT(Timestamp desdeT) {
        this.desdeT = desdeT;
    }

    public Timestamp getHastaT() {
        return hastaT;
    }

    public void setHastaT(Timestamp hastaT) {
        this.hastaT = hastaT;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }
}
