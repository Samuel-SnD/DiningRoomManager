package com.example.diningroommanager;

import com.example.diningroommanager.mapping.Token;

public class Session {
    Token tk;
    private static Session instance = new Session();

    private Session() {}

    public static Session getInstance() {
        return instance;
    }
}
