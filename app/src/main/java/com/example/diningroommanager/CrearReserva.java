package com.example.diningroommanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.diningroommanager.mapping.Mesa;

import java.util.ArrayList;

public class CrearReserva extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_reserva);

        TextView mesa = findViewById(R.id.tvMesaActual);
        Intent it = getIntent();
        int position = it.getIntExtra("posicion", 0);
        ArrayList <Mesa> arrMesas = (ArrayList<Mesa>) it.getSerializableExtra("array");
    }
}