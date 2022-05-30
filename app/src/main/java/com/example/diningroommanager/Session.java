package com.example.diningroommanager;

import com.example.diningroommanager.mapping.Usuario;

public class Session {
    Usuario user;
    private static Session instance;

    private Session() {}

    public static Session getInstance(){
        if(instance == null){
            instance = new Session();
        }
        return instance;
    }
}
