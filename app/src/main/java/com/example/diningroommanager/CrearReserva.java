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
import android.widget.TextView;
import android.widget.Toast;

import com.example.diningroommanager.mapping.Mesa;
import com.example.diningroommanager.mapping.ReservaCreate;
import com.google.gson.Gson;

import java.util.ArrayList;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class CrearReserva extends AppCompatActivity {

    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_reserva);

        // Recupero las sharedPreferences
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        // Declaración de variables
        TextView mesa = findViewById(R.id.tvMesaActual);
        EditText etDay = findViewById(R.id.etDay);
        EditText etHour = findViewById(R.id.etHour);
        Button btnReserva = findViewById(R.id.btnHacerReserva);

        // Recogo el intent y creo un arraylist con las mesas y actualizo el texto de la textView de la mesa
        Intent it = getIntent();
        int position = it.getIntExtra("posicion", 0);
        ArrayList <Mesa> arrMesas = (ArrayList<Mesa>) it.getSerializableExtra("array");
        String placeHolder = "Mesa: " + arrMesas.get(position).getId();
        mesa.setText(placeHolder);


        // En caso de apretar el botón de crear una reserva, se realiza una petición a la API
        // que crea la reserva con los datos proporcionados
        btnReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReservaCreate reserva = new ReservaCreate(arrMesas.get(position).getId(), etDay.getText().toString().substring(0, 10), etHour.getText().toString().substring(0, 5));

                HttpResponse res = Unirest.post("http://diningroommanager.live:8000/reservas")
                        .header("Authorization", "Bearer " + sharedPref.getString("Token", ""))
                        .header("Content-Type","application/json")
                        .header("accept", "Application/json")
                        .body(new Gson().toJson(reserva))
                        .asJson();
                if (res.getStatus() == 200) {
                    Toast.makeText(getApplicationContext(), "Reserva creada correctamente", Toast.LENGTH_SHORT).show();
                } else if (res.getStatus() == 409){
                    Toast.makeText(getApplicationContext(), "Reserva ya creada", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No se ha podido crear la reserva", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}