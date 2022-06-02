package com.example.diningroommanager;

import android.content.Context;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diningroommanager.mapping.Usuario;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import unirest.shaded.com.google.gson.Gson;

public class Utils {

    // Clase de utilidades, usada exclusivamente en este momento para acceder al usuario actual
    public static Usuario getUsuario (AppCompatActivity activity) {
        Usuario user;
        try {
            user = Session.getInstance().user;
        } catch (NullPointerException e) {
            HttpResponse res = Unirest.get("http://diningroommanager.live:8000/users/me")
                    .header("Authorization", "Bearer " + PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext()).getString("Token", ""))
                    .header("accept", "Application/json")
                    .asJson();
            user = new Gson().fromJson(res.getBody().toString(), Usuario.class);
        }
        return user;
    }
}
