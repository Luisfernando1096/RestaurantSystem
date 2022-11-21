package com.restaurante.entidades;

import com.restaurante.anotaciones.*;
import java.sql.Date;

@Entity(table = "facturas")
public class Facturas {
    @AutoIncrement
    @PrimaryKey
    private int idFactura;
    @NotNull
    private Date fecha;
    @NotNull
    private java.math.BigDecimal  total;
    @NotNull
    private int idCliente;
    @NotNull
    private int idFormaPago;
    @NotNull
    private java.math.BigDecimal efectivo;
    @NotNull
    private java.math.BigDecimal cambio;
    @NotNull
    private String estado;
    @NotNull
    private int idTarjeta;

    public Facturas() {
    }

    public Facturas(int idFactura, Date fecha, java.math.BigDecimal total, int idCliente, int idFormaPago, java.math.BigDecimal efectivo, java.math.BigDecimal cambio, String estado, int idTarjeta) {
        this.idFactura = idFactura;
        this.fecha = fecha;
        this.total = total;
        this.idCliente = idCliente;
        this.idFormaPago = idFormaPago;
        this.efectivo = efectivo;
        this.cambio = cambio;
        this.estado = estado;
        this.idTarjeta = idTarjeta;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public java.math.BigDecimal getTotal() {
        return total;
    }

    public void setTotal(java.math.BigDecimal total) {
        this.total = total;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdFormaPago() {
        return idFormaPago;
    }

    public void setIdFormaPago(int idFormaPago) {
        this.idFormaPago = idFormaPago;
    }

    public java.math.BigDecimal getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(java.math.BigDecimal efectivo) {
        this.efectivo = efectivo;
    }

    public java.math.BigDecimal getCambio() {
        return cambio;
    }

    public void setCambio(java.math.BigDecimal cambio) {
        this.cambio = cambio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(int idTarjeta) {
        this.idTarjeta = idTarjeta;
    }
    
}
