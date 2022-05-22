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
}
