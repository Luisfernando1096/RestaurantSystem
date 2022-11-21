package com.restaurante.entidades;

import com.restaurante.anotaciones.*;

@Entity(table = "roles")
public class Roles {
    @AutoIncrement
    @PrimaryKey
    private int idRol;
    @NotNull
    private String rol;

    public Roles() {
    }

    public Roles(int idRol, String rol) {
        this.idRol = idRol;
        this.rol = rol;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
    
}
