package com.example.diningroommanager.mapping;

import java.io.Serializable;

public class ReservaCreate implements Serializable {
    int mesa;
    String fecha;

    public ReservaCreate(int mesa, String fecha) {
        this.mesa = mesa;
        this.fecha = fecha;
    }

    public ReservaCreate() {
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
}
