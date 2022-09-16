package com.uso.detodo.models;

import java.util.Calendar;

public class Horario {
    private String dias;
    private String desde;
    private String hasta;
    private HorarioType tipo;
    //se manejarán con la clase Calendar
    private int dia_inicio;
    private int dia_fin;
    private int hora_inicio;
    private int minuto_inicio;
    private int hora_fin;
    private int minuto_fin;

    public Horario(String dias, String desde, String hasta) {
        this.dias = dias;
        this.desde = desde;
        this.hasta = hasta;
        tipo = HorarioType.APERTURA;
    }

    public Horario(String dias, String desde, String hasta, HorarioType tipo) {
        this.dias = dias;
        this.desde = desde;
        this.hasta = hasta;
        this.tipo = tipo;
    }

    public Horario(HorarioType tipo, int dia_inicio, int dia_fin, int hora_inicio, int minuto_inicio, int hora_fin, int minuto_fin) {
        this.tipo = tipo;
        this.dia_inicio = dia_inicio;
        this.dia_fin = dia_fin;
        this.hora_inicio = hora_inicio;
        this.minuto_inicio = minuto_inicio;
        this.hora_fin = hora_fin;
        this.minuto_fin = minuto_fin;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public String getDesde() {
        return desde;
    }

    public void setDesde(String desde) {
        this.desde = desde;
    }

    public String getHasta() {
        return hasta;
    }

    public void setHasta(String hasta) {
        this.hasta = hasta;
    }

    public HorarioType getTipo() {
        return tipo;
    }

    public void setTipo(HorarioType tipo) {
        this.tipo = tipo;
    }

    public int getDia_inicio() {
        return dia_inicio;
    }

    public void setDia_inicio(int dia_inicio) {
        this.dia_inicio = dia_inicio;
    }

    public int getDia_fin() {
        return dia_fin;
    }

    public void setDia_fin(int dia_fin) {
        this.dia_fin = dia_fin;
    }

    public int getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(int hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public int getMinuto_inicio() {
        return minuto_inicio;
    }

    public void setMinuto_inicio(int minuto_inicio) {
        this.minuto_inicio = minuto_inicio;
    }

    public int getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(int hora_fin) {
        this.hora_fin = hora_fin;
    }

    public int getMinuto_fin() {
        return minuto_fin;
    }

    public void setMinuto_fin(int minuto_fin) {
        this.minuto_fin = minuto_fin;
    }
}
