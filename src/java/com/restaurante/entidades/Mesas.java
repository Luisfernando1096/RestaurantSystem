package com.restaurante.entidades;

import com.restaurante.anotaciones.*;

@Entity(table = "mesas")
public class Mesas {
    @PrimaryKey
    @AutoIncrement
    private int idMesa;
    @NotNull
    private String caracteristica;
    @NotNull
    private int capacidad;
    @NotNull
    private int idSucursal;

    public Mesas() {
    }

    public Mesas(int idMesa, String caracteristica, int capacidad, int idSucursal) {
        this.idMesa = idMesa;
        this.caracteristica = caracteristica;
        this.capacidad = capacidad;
        this.idSucursal = idSucursal;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public String getCaracteristica() {
        return caracteristica;
    }

    public void setCaracteristica(String caracteristica) {
        this.caracteristica = caracteristica;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    
    
}
