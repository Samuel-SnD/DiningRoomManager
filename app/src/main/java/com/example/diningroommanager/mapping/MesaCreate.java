package com.example.diningroommanager.mapping;

import java.io.Serializable;

public class MesaCreate implements Serializable {
    int asientos;
    int idComedor;

    public MesaCreate(int asientos, int comedor) {
        this.asientos = asientos;
        this.idComedor = comedor;
    }

    public MesaCreate() {
    }

    public int getAsientos() {
        return asientos;
    }

    public void setAsientos(int asientos) {
        this.asientos = asientos;
    }

    public int getIdComedor() {
        return idComedor;
    }

    public void setIdComedor(int idComedor) {
        this.idComedor = idComedor;
    }
}
