package com.example.habitproproject;

public class Dia {
    private String nombreAbreviado;
    private int numeroDia;

    public Dia(String nombreAbreviado, int numeroDia) {
        this.nombreAbreviado = nombreAbreviado;
        this.numeroDia = numeroDia;
    }

    public String getNombreAbreviado() {
        return nombreAbreviado;
    }

    public int getNumeroDia() {
        return numeroDia;
    }
}
