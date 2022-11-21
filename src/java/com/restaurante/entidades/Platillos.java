package com.restaurante.entidades;
import com.restaurante.anotaciones.*;

@Entity(table = "platillos")
public class Platillos {
    @AutoIncrement
    @PrimaryKey
    private int idPlatillo;
    @NotNull
    private String platillo;
    @NotNull
    private int idClasificacion;
    @NotNull
    @FieldName(name = "precio_unitario")
    private java.math.BigDecimal precio;
    @NotNull
    private int existencia;

    public Platillos() {
    }

    public Platillos(int idPlatillo, String platillo, int idClasificacion, java.math.BigDecimal precio, int existencia) {
        this.idPlatillo = idPlatillo;
        this.platillo = platillo;
        this.idClasificacion = idClasificacion;
        this.precio = precio;
        this.existencia = existencia;
    }

    public int getIdPlatillo() {
        return idPlatillo;
    }

    public void setIdPlatillo(int idPlatillo) {
        this.idPlatillo = idPlatillo;
    }

    public String getPlatillo() {
        return platillo;
    }

    public void setPlatillo(String platillo) {
        this.platillo = platillo;
    }

    public int getIdClasificacion() {
        return idClasificacion;
    }

    public void setIdClasificacion(int idClasificacion) {
        this.idClasificacion = idClasificacion;
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
