package com.example.diningroommanager.mapping;

import java.io.Serializable;

public class MesaCreate implements Serializable {
    int asientos;

    public MesaCreate(int asientos) {
        this.asientos = asientos;
    }

    public MesaCreate() {
    }

    public int getAsientos() {
        return asientos;
    }

    public void setAsientos(int asientos) {
        this.asientos = asientos;
    }
}
