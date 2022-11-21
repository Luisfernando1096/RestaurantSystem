package com.restaurante.entidades;

import com.restaurante.anotaciones.*;
import java.sql.Timestamp;

@Entity(table = "reservas")
public class Reservas {

    @AutoIncrement
    @PrimaryKey
    private int idReserva;
    @NotNull
    private Timestamp fecha;
    @NotNull
    private String idCliente;

    public Reservas() {
    }

    public Reservas(int idReserva, Timestamp fecha, String idCliente) {
        this.idReserva = idReserva;
        this.fecha = fecha;
        this.idCliente = idCliente;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

}
