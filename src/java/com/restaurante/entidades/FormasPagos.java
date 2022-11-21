package com.restaurante.entidades;

import com.restaurante.anotaciones.*;

@Entity(table = "formas_pagos")
public class FormasPagos {
    @PrimaryKey
    @AutoIncrement
    private int idFormaPago;
    @NotNull
    private String forma;

    public FormasPagos() {
    }

    public FormasPagos(int idFormaPago, String forma) {
        this.idFormaPago = idFormaPago;
        this.forma = forma;
    }

    public int getIdFormaPago() {
        return idFormaPago;
    }

    public void setIdFormaPago(int idFormaPago) {
        this.idFormaPago = idFormaPago;
    }

    public String getForma() {
        return forma;
    }

    public void setForma(String forma) {
        this.forma = forma;
    }
    
}
