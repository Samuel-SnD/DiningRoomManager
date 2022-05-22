package com.example.diningroommanager.mapping;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Usuario implements Serializable {
    String nombre;
    String apellidos;
    String correo;
    int id;

    @SerializedName("is_Admin")
    int isAdmin;

    public Usuario(String nombre, String apellidos, String correo, int isAdmin) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.isAdmin = isAdmin;
    }

    public Usuario() {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }
}
