package com.restaurante.entidades;

import com.restaurante.anotaciones.*;
import java.sql.Timestamp;

@Entity(table = "ordenes")
public class Ordenes {
    @PrimaryKey
    @AutoIncrement
    private int idOrden;
    @NotNull
    private Timestamp fecha_orden;
    @NotNull
    private String idCliente;
    @NotNull
    private String idEmpleado;
    @NotNull
    private String estado_orden;
    @NotNull
    private int idTipo;
    @NotNull
    private Timestamp fecha_envio;

    public Ordenes() {
    }

    public Ordenes(int idOrden, Timestamp fecha_orden, String idCliente, String idEmpleado, String estado_orden, int idTipo, Timestamp fecha_envio) {
        this.idOrden = idOrden;
        this.fecha_orden = fecha_orden;
        this.idCliente = idCliente;
        this.idEmpleado = idEmpleado;
        this.estado_orden = estado_orden;
        this.idTipo = idTipo;
        this.fecha_envio = fecha_envio;
    }

    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public Timestamp getFecha_orden() {
        return fecha_orden;
    }

    public void setFecha_orden(Timestamp fecha_orden) {
        this.fecha_orden = fecha_orden;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getEstado_orden() {
        return estado_orden;
    }

    public void setEstado_orden(String estado_orden) {
        this.estado_orden = estado_orden;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public Timestamp getFecha_envio() {
        return fecha_envio;
    }

    public void setFecha_envio(Timestamp fecha_envio) {
        this.fecha_envio = fecha_envio;
    }
    
}
