package com.restaurante.entidades;

import com.restaurante.anotaciones.*;

@Entity(table = "postres")
public class Postres {
    @AutoIncrement
    @PrimaryKey
    private int idPostre;
    @NotNull
    private String postre;
    @NotNull
    private java.math.BigDecimal precio;
    @NotNull
    private int existencia;

    public Postres() {
    }

    public Postres(int idPostre, String postre, java.math.BigDecimal precio, int existencia) {
        this.idPostre = idPostre;
        this.postre = postre;
        this.precio = precio;
        this.existencia = existencia;
    }

    public int getIdPostre() {
        return idPostre;
    }

    public void setIdPostre(int idPostre) {
        this.idPostre = idPostre;
    }

    public String getPostre() {
        return postre;
    }

    public void setPostre(String postre) {
        this.postre = postre;
    }

    public java.math.BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(java.math.BigDecimal precio) {
        this.precio = precio;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }
    
    
}
