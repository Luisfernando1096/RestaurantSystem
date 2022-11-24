package com.restaurante.entidades;

import com.restaurante.anotaciones.*;
import java.math.BigDecimal;

@Entity(table = "platillo_orden")
public class PlatilloOrden {
    @PrimaryKey
    @AutoIncrement
    private int idPlatilloOrden;
    @NotNull
    private int idPlatillo;
    @NotNull
    private int idOrden;
    @NotNull
    private int cantidad;
    @NotNull
    private BigDecimal sub_total;

    public PlatilloOrden() {
    }

    public int getIdPlatilloOrden() {
        return idPlatilloOrden;
    }

    public void setIdPlatilloOrden(int idPlatilloOrden) {
        this.idPlatilloOrden = idPlatilloOrden;
    }

    public int getIdPlatillo() {
        return idPlatillo;
    }

    public void setIdPlatillo(int idPlatillo) {
        this.idPlatillo = idPlatillo;
    }

    public PlatilloOrden(int idPlatilloOrden, int idPlatillo, int idOrden, int cantidad, BigDecimal sub_total) {
        this.idPlatilloOrden = idPlatilloOrden;
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

    public BigDecimal getSub_total() {
        return sub_total;
    }

    public void setSub_total(BigDecimal sub_total) {
        this.sub_total = sub_total;
    }
    
    
}
