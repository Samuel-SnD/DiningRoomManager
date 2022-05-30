package com.example.diningroommanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diningroommanager.mapping.MenuCreate;
import com.google.gson.Gson;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class CrearMenu extends AppCompatActivity {

    SharedPreferences sharedPref;
    String comedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_menu);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Button btnCrear = findViewById(R.id.btnCrearMenu);
        EditText etNombre = findViewById(R.id.etNombreMenu);
        EditText etPlatos = findViewById(R.id.etPlatosMenu);
        EditText etBebidas = findViewById(R.id.etBebidasMenu);
        EditText etIdComedor = findViewById(R.id.etComedorMenu);
        Intent it = getIntent();
        comedor = it.getStringExtra("comedor");

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuCreate menu = new MenuCreate(Integer.parseInt(etNombre.getText().toString()), etPlatos.getText().toString(), etBebidas.getText().toString(), Integer.parseInt(etIdComedor.getText().toString()));
                HttpResponse res = Unirest.post("http://diningroommanager.live:8000/menus")
                        .header("Authorization", "Bearer " + sharedPref.getString("Token", ""))
                        .header("Content-Type","application/json")
                        .header("accept", "Application/json")
                        .body(new Gson().toJson(menu))
                        .asJson();
                if (res.getStatus() == 200) {
                    Toast.makeText(getApplicationContext(), "Menu creado correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No se ha podido crear el menu", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}