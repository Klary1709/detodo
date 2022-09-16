package com.uso.detodo.models;

import com.uso.detodo.ContactType;

public class Contacto {
    private String contacto;
    private String valor;
    private ContactType tipo;
    private int idicono;

    public Contacto(String contacto, String valor, ContactType tipo, int idicono) {
        this.contacto = contacto;
        this.valor = valor;
        this.tipo = tipo;
        this.idicono = idicono;
    }

    public Contacto(String contacto, String valor, ContactType tipo) {
        this.contacto = contacto;
        this.valor = valor;
        this.tipo = tipo;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getValor() {
        return valor;
    }

    public ContactType getTipo() {
        return tipo;
    }

    public void setTipo(ContactType tipo) {
        this.tipo = tipo;
    }

    public int getIdicono() {
        return idicono;
    }

    public void setIdicono(int idicono) {
        this.idicono = idicono;
    }
}
