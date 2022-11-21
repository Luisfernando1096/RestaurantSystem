package com.restaurante.entidades;

import com.restaurante.anotaciones.*;

@Entity(table = "clasificaciones")
public class Clasificaciones {
    @PrimaryKey
    @AutoIncrement
    private  int idClasificacion;
    @NotNull
    private String clasificacion;

    public Clasificaciones() {
    }

    public Clasificaciones(int idClasificacion, String clasificacion) {
        this.idClasificacion = idClasificacion;
        this.clasificacion = clasificacion;
    }

    public int getIdClasificacion() {
        return idClasificacion;
    }

    public void setIdClasificacion(int idClasificacion) {
        this.idClasificacion = idClasificacion;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }
    
}
