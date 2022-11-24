package com.restaurante.entidades;

import com.restaurante.anotaciones.*;
import java.math.BigDecimal;

@Entity(table = "postre_orden")
public class PostreOrden {
    @PrimaryKey
    @AutoIncrement
    private int idPostreOrden;
    @NotNull
    private int idPostre;
    @NotNull
    private int idOrden;
    @NotNull
    private int cantidad;
    @NotNull
    private BigDecimal sub_total;

    public PostreOrden() {
    }

    public int getIdPostreOrden() {
        return idPostreOrden;
    }

    public void setIdPostreOrden(int idPostreOrden) {
        this.idPostreOrden = idPostreOrden;
    }

    public PostreOrden(int idPostreOrden, int idPostre, int idOrden, int cantidad, BigDecimal sub_total) {
        this.idPostreOrden = idPostreOrden;
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

    public BigDecimal getSub_total() {
        return sub_total;
    }

    public void setSub_total(BigDecimal sub_total) {
        this.sub_total = sub_total;
    }
    
    
}
