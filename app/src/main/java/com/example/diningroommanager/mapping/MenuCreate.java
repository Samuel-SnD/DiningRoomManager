package com.example.diningroommanager.mapping;

import java.io.Serializable;

public class MenuCreate implements Serializable {
    int nombre;
    String platos;
    String bebidas;
    int idComedor;

    public MenuCreate(int nombre, String platos, String bebidas, int idComedor) {
        this.nombre = nombre;
        this.platos = platos;
        this.bebidas = bebidas;
        this.idComedor = idComedor;
    }

    public MenuCreate() {
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public String getPlatos() {
        return platos;
    }

    public void setPlatos(String platos) {
        this.platos = platos;
    }

    public String getBebidas() {
        return bebidas;
    }

    public void setBebidas(String bebidas) {
        this.bebidas = bebidas;
    }

    public int getIdComedor() {
        return idComedor;
    }

    public void setIdComedor(int idComedor) {
        this.idComedor = idComedor;
    }
}
