package com.restaurante.entidades;

import com.restaurante.anotaciones.*;
import java.sql.Date;

@Entity(table = "tarjetas")
public class Tarjetas {

    @PrimaryKey
    @AutoIncrement
    private int idTarjeta;
    @NotNull
    private String digitos;
    @NotNull
    private Date fechaExp;
    @NotNull
    private String propietario;
    @NotNull
    private int cvc;

    public Tarjetas() {
    }

    public Tarjetas(int idTarjeta, String digitos, Date fechaExp, String propietario, int cvc) {
        this.idTarjeta = idTarjeta;
        this.digitos = digitos;
        this.fechaExp = fechaExp;
        this.propietario = propietario;
        this.cvc = cvc;
    }

    public int getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(int idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public String getDigitos() {
        return digitos;
    }

    public void setDigitos(String digitos) {
        this.digitos = digitos;
    }

    public Date getFechaExp() {
        return fechaExp;
    }

    public void setFechaExp(Date fechaExp) {
        this.fechaExp = fechaExp;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public int getCvc() {
        return cvc;
    }

    public void setCvc(int cvc) {
        this.cvc = cvc;
    }

}
