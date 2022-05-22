package com.example.diningroommanager.mapping;

import java.io.Serializable;

public class ComedorCreate implements Serializable {
    String ajustes;

    public ComedorCreate(String ajustes) {
        this.ajustes = ajustes;
    }

    public ComedorCreate() {
    }

    public String getAjustes() {
        return ajustes;
    }

    public void setAjustes(String ajustes) {
        this.ajustes = ajustes;
    }
}
