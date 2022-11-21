package com.restaurante.entidades;

import com.restaurante.anotaciones.*;

@Entity(table = "postre_orden")
public class PostreOrden {
    @NotNull
    int idPostre;
    @NotNull
    int idOrden;
    @NotNull
    int cantidad;
    @NotNull
    double sub_total;

    public PostreOrden() {
    }

    public PostreOrden(int idPostre, int idOrden, int cantidad, double sub_total) {
        this.idPostre = idPostre;
        this.idOrden = idOrden;
        this.cantidad = cantidad;
        this.sub_total = sub_total;
    }

    public int getIdPostre() {
        return idPostre;
    }

    public void setIdPostre(int idPostre) {
        this.idPostre = idPostre;
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
