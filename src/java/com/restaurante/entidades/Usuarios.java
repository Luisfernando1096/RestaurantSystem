package com.restaurante.entidades;

import com.restaurante.anotaciones.*;

@Entity(table = "usuarios")
public class Usuarios {

    @PrimaryKey
    private String usuario;
    @NotNull
    private String nombres;
    @NotNull
    private String apellidos;
    @NotNull
    private String correo;
    @NotNull
    private String telefono;
    @NotNull
    private String password;
    @NotNull
    private int idRol;

    public Usuarios() {
    }

    public Usuarios(String usuario, String nombres, String apellidos, String correo, String telefono, String password, int idRol) {
        this.usuario = usuario;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.telefono = telefono;
        this.password = password;
        this.idRol = idRol;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

}
