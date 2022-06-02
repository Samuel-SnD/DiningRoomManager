package com.example.diningroommanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import kong.unirest.RawResponse;
import kong.unirest.Unirest;
import unirest.shaded.com.google.gson.Gson;

public class Reservas extends AppCompatActivity {

    // Declaración de variables
    Usuario user = MainScreen.user;
    static ArrayList <Reserva> reservas = new ArrayList <Reserva> ();
    static ListView lvReservas;
    SharedPreferences sharedPref;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);

        // Recuperación las sharedPreferences y de la textView del nombre del usuario
        // para hacer que aparezca el nombre del mismo en la activity
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        TextView tvNombre = findViewById(R.id.tvUsuarioReserva);
        tvNombre.setText("Reservas de " + user.getNombre());

        // Creo un adapter para la listView y lo establezco, también la registro para el contextMenu
        lvReservas = findViewById(R.id.lvReservas);
        ListAdapter2 lAdapter = new ListAdapter2(this, reservas);
        lvReservas.setAdapter(lAdapter);
        registerForContextMenu(lvReservas);

        // Actualizo el array de reservas con las que existan en ese momento
        updateArrayReservas();

    }

    // Método que actualiza las reservas actuales realizando una petición a la API
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateArrayReservas () {
        HttpResponse res3 = Unirest.get("http://diningroommanager.live:8000/reservas/user/" + user.getId())
                .header("Authorization", "Bearer " + sharedPref.getString("Token", ""))
                .header("accept", "application/json")
                .asJson();

        ArrayList<Reserva> arrreservas = new Gson().fromJson(res3.getBody().toString(), new TypeToken<ArrayList<Reserva>>(){}.getType());
        reservas.clear();
        arrreservas.forEach(reserva -> {
            reservas.add(reserva);
        });
    }

    // Estos dos métodos crean un menú contextual
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
            // Si el usuario es administrador, borro la reserva seleccionada mediante una petición a la API
            case R.id.menudeletereservaitem1:
                if (user.getIsAdmin() == 1) {
                    int reserva = reservas.get(listPosition).getId();
                    HttpResponse res = Unirest.delete("http://diningroommanager.live:8000/reservas/" + reserva)
                            .header("Authorization", "Bearer " + sharedPref.getString("Token", ""))
                            .header("accept", "application/json")
                            .asJson();
                     if (res.getStatus() == 200) {
                        Toast.makeText(getApplicationContext(), "Reserva eliminada", Toast.LENGTH_SHORT).show();
                        lvReservas.invalidateViews();
                        updateArrayReservas();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Solo los administradores pueden realizar esa accíon", Toast.LENGTH_SHORT).show();
                } return true;
            // Si el usuario quiere descargar un pdf de la reserva se redirige al navegador con la
            // url de descarga del mismo
            case R.id.menudeletereservaitem2:
                String url = "http://diningroommanager.live:8000/reservas/" + reservas.get(listPosition).getId() + "/pdf";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            default:
                return super.onContextItemSelected(item);
        }
    }
}