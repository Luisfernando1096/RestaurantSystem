package com.restaurante.entidades;

import com.restaurante.anotaciones.*;

@Entity(table = "permisos")
public class Permisos {
    @PrimaryKey
    @AutoIncrement
    private int idPermiso;
    @NotNull
    private int idMenu;
    @NotNull
    private int idRol;

    public Permisos() {
    }

    public Permisos(int idPermiso, int idMenu, int idRol) {
        this.idPermiso = idPermiso;
        this.idMenu = idMenu;
        this.idRol = idRol;
    }

    public int getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(int idPermiso) {
        this.idPermiso = idPermiso;
    }

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }
    
    
}
