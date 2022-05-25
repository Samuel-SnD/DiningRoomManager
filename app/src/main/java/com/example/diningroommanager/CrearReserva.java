package com.example.diningroommanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_reserva);


        TextView mesa = findViewById(R.id.tvMesaActual);
        EditText etDay = findViewById(R.id.etDay);
        EditText etHour = findViewById(R.id.etHour);
        Button btnReserva = findViewById(R.id.btnHacerReserva);
        Button btnVolver = findViewById(R.id.btnVolverReserva);
        Intent it = getIntent();
        int position = it.getIntExtra("posicion", 0);
        ArrayList <Mesa> arrMesas = (ArrayList<Mesa>) it.getSerializableExtra("array");
        String placeHolder = "Mesa: " + arrMesas.get(position).getId();
        mesa.setText(placeHolder);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), MainScreen.class);
                startActivity(it);
            }
        });

        btnReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReservaCreate reserva = new ReservaCreate(arrMesas.get(position).getId(), etDay.getText().toString().substring(0, 10), etHour.getText().toString().substring(0, 5));

                HttpResponse res = Unirest.post("http://diningroommanager.live:8000/reservas")
                        .header("Authorization", "Bearer " + Session.getInstance().tk.getAccessToken())
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