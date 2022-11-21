package com.restaurante.entidades;

import com.restaurante.anotaciones.*;

@Entity(table = "menus")
public class Menus {

    @PrimaryKey
    @AutoIncrement
    private int idMenu;
    @NotNull
    private String menu;
    private String descripcion;
    @NotNull
    private String url;
    @NotNull
    private int idPadre;

    public Menus() {
    }

    public Menus(int idMenu, String menu, String descripcion, String url, int idPadre) {
        this.idMenu = idMenu;
        this.menu = menu;
        this.descripcion = descripcion;
        this.url = url;
        this.idPadre = idPadre;
    }

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(int idPadre) {
        this.idPadre = idPadre;
    }
    
    
}
