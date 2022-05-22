package com.example.diningroommanager.mapping;

import java.io.Serializable;

public class UsuarioCreate implements Serializable {
    String nombre;
    String apellidos;
    String correo;
    String contraseña;

    public UsuarioCreate(String nombre, String apellidos, String correo, String contraseña) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.contraseña = contraseña;
    }

    public UsuarioCreate() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
