package com.restaurante.entidades;

import com.restaurante.anotaciones.*;

@Entity(table = "sucursales")
public class Sucursales {
    @PrimaryKey
    @AutoIncrement
    private int idSucursal;
    @NotNull
    private String nombre;
    @NotNull
    private String ubicacion;
    @NotNull
    private String telefono;

    public Sucursales() {
    }

    public Sucursales(int idSucursal, String nombre, String ubicacion, String telefono) {
        this.idSucursal = idSucursal;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.telefono = telefono;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    
}
