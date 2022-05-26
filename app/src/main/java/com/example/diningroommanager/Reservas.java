package com.example.diningroommanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diningroommanager.mapping.Mesa;
import com.example.diningroommanager.mapping.Reserva;
import com.example.diningroommanager.mapping.Usuario;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import unirest.shaded.com.google.gson.Gson;

public class Reservas extends AppCompatActivity {

    Usuario user = MainScreen.user;
    static ArrayList <Reserva> reservas = new ArrayList <Reserva> ();
    static ListView lvReservas;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);

        TextView tvNombre = findViewById(R.id.tvUsuarioReserva);
        Button btnVolver = findViewById(R.id.btnVolverReservasUsuario);

        tvNombre.setText("Reservas de " + user.getNombre());

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), MainScreen.class);
                startActivity(it);
            }
        });

        lvReservas = findViewById(R.id.lvReservas);
        ListAdapter2 lAdapter = new ListAdapter2(getApplicationContext(), reservas);
        lvReservas.setAdapter(lAdapter);
        registerForContextMenu(lvReservas);

        updateArrayReservas();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateArrayReservas () {

        HttpResponse res3 = Unirest.get("http://diningroommanager.live:8000/reservas/user/" + user.getId())
                .header("Authorization", "Bearer " + Session.getInstance().tk.getAccessToken())
                .header("accept", "Application/json")
                .asJson();

        ArrayList<Reserva> arrreservas = new Gson().fromJson(res3.getBody().toString(), new TypeToken<ArrayList<Reserva>>(){}.getType());
        reservas.clear();
        arrreservas.forEach(reserva -> {
            reservas.add(reserva);
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menudeletereserva, menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int listPosition = info.position;
        switch (item.getItemId()) {
            case R.id.menudeletereservaitem1:
                if (user.getIsAdmin() == 1) {
                    int reserva = reservas.get(listPosition).getId();
                    HttpResponse res = Unirest.delete("http://diningroommanager.live:8000/reservas/" + reserva)
                            .header("Authorization", "Bearer " + Session.getInstance().tk.getAccessToken())
                            .header("accept", "Application/json")
                            .asJson();
                     if (res.getStatus() == 200) {
                        Toast.makeText(getApplicationContext(), "Reserva eliminada", Toast.LENGTH_SHORT).show();
                        lvReservas.invalidateViews();
                        updateArrayReservas();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Solo los administradores pueden realizar esa accíon", Toast.LENGTH_SHORT).show();
                } return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}