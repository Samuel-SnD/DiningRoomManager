package com.example.diningroommanager.mapping;

import java.io.Serializable;

public class Comedor implements Serializable {
    String ajustes;
    int id;

    public Comedor(String ajustes) {
        this.ajustes = ajustes;
    }

    public Comedor() {
    }

    public String getAjustes() {
        return ajustes;
    }

    public void setAjustes(String ajustes) {
        this.ajustes = ajustes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
