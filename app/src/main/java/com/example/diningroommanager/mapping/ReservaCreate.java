package com.example.diningroommanager.mapping;

import java.io.Serializable;

public class ReservaCreate implements Serializable {
    int mesa;
    String fecha;
    String hora;

    public ReservaCreate(int mesa, String fecha, String hora) {
        this.mesa = mesa;
        this.fecha = fecha;
        this.hora = hora;
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

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
