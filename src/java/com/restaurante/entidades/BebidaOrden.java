package com.restaurante.entidades;

import com.restaurante.anotaciones.*;
import java.math.BigDecimal;

@Entity(table = "bebida_orden")
public class BebidaOrden {
    @PrimaryKey
    @AutoIncrement
    private int idBebidaOrden;
    @NotNull
    private int idBebida;
    @NotNull
    private int idOrden;
    @NotNull
    private int cantidad;
    @NotNull
    private BigDecimal sub_total;

    public BebidaOrden() {
    }

    public BebidaOrden(int idBebidaOrden, int idBebida, int idOrden, int cantidad, BigDecimal sub_total) {
        this.idBebidaOrden = idBebidaOrden;
        this.idBebida = idBebida;
        this.idOrden = idOrden;
        this.cantidad = cantidad;
        this.sub_total = sub_total;
    }

    public int getIdBebidaOrden() {
        return idBebidaOrden;
    }

    public void setIdBebidaOrden(int idBebidaOrden) {
        this.idBebidaOrden = idBebidaOrden;
    }

    public int getIdBebida() {
        return idBebida;
    }

    public void setIdBebida(int idBebida) {
        this.idBebida = idBebida;
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
