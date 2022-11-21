package com.restaurante.entidades;

import com.restaurante.anotaciones.*;

@Entity(table = "platillo_orden")
public class PlatilloOrden {
    @NotNull
    int idPlatillo;
    @NotNull
    int idOrden;
    @NotNull
    int cantidad;
    @NotNull
    double sub_total;

    public PlatilloOrden() {
    }

    public PlatilloOrden(int idPlatillo, int idOrden, int cantidad, double sub_total) {
        this.idPlatillo = idPlatillo;
        this.idOrden = idOrden;
        this.cantidad = cantidad;
        this.sub_total = sub_total;
    }

    public int getIdPostre() {
        return idPlatillo;
    }

    public void setIdPostre(int idPostre) {
        this.idPlatillo = idPostre;
    }

    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSub_total() {
        return sub_total;
    }

    public void setSub_total(double sub_total) {
        this.sub_total = sub_total;
    }
    
    
}
