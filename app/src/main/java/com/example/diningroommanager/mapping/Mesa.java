package com.example.diningroommanager.mapping;

import java.io.Serializable;

public class Mesa implements Serializable {
    int asientos;
    int id;

    public Mesa(int asientos) {
        this.asientos = asientos;
    }

    public Mesa() {
    }

    public int getAsientos() {
        return asientos;
    }

    public void setAsientos(int asientos) {
        this.asientos = asientos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}