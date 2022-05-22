package com.example.diningroommanager.mapping;

import java.io.Serializable;

public class Reserva implements Serializable {
    int mesa;
    String fecha;
    int id;
    int usuario;

    public Reserva(int mesa, String fecha, int usuario) {
        this.mesa = mesa;
        this.fecha = fecha;
        this.usuario = usuario;
    }

    public Reserva() {
    }

    public int getMesa() {
        return mesa;
    }

    public void setMesa(int mesa) {
        this.mesa = mesa;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }
}
