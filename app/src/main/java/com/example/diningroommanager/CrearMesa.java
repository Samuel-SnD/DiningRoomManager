package com.example.diningroommanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diningroommanager.mapping.Mesa;
import com.example.diningroommanager.mapping.MesaCreate;
import com.google.gson.Gson;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class CrearMesa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_mesa);

        EditText etAsientos = findViewById(R.id.etAsientos);
        EditText etIdComedor = findViewById(R.id.etidComedorMesa);
        Button btnCrearMesa = findViewById(R.id.btnCrearMesa);
        Button btnVolver = findViewById(R.id.btnVolverCrearMesa);

        btnCrearMesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MesaCreate mesa = new MesaCreate(Integer.parseInt(etAsientos.getText().toString()), Integer.parseInt(etIdComedor.getText().toString()));
                HttpResponse res = Unirest.post("http://diningroommanager.live:8000/mesas")
                        .header("Authorization", "Bearer " + Session.getInstance().tk.getAccessToken())
                        .header("Content-Type","application/json")
                        .header("accept", "Application/json")
                        .body(new Gson().toJson(mesa))
                        .asJson();
                if (res.getStatus() == 200) {
                    Toast.makeText(getApplicationContext(), "Mesa creada correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No se ha podido crear la mesa", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), MainScreen.class);
                startActivity(it);
            }
        });
    }
}