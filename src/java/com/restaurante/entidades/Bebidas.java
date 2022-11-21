package com.restaurante.entidades;

import com.restaurante.anotaciones.*;

@Entity(table = "bebidas")
public class Bebidas {
    @NotNull
    @PrimaryKey
    @AutoIncrement
    private int idBebida;
    @NotNull
    private String nombre;
    @NotNull
    private java.math.BigDecimal precio;
    @NotNull
    private int existencia;

    public Bebidas() {
    }

    public Bebidas(int idBebida, String nombre, java.math.BigDecimal precio, int existencia) {
        this.idBebida = idBebida;
        this.nombre = nombre;
        this.precio = precio;
        this.existencia = existencia;
    }

    public int getIdBebida() {
        return idBebida;
    }

    public void setIdBebida(int idBebida) {
        this.idBebida = idBebida;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
