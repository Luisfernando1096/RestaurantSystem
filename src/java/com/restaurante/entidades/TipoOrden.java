package com.restaurante.entidades;

import com.restaurante.anotaciones.*;

@Entity(table = "tipo_orden")
public class TipoOrden {
    @PrimaryKey
    @AutoIncrement
    private int idTipo;
    @NotNull
    private String tipo;

    public TipoOrden() {
    }

    public TipoOrden(int idTipo, String tipo) {
        this.idTipo = idTipo;
        this.tipo = tipo;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
      
}
