package com.example.diningroommanager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diningroommanager.mapping.Comedor;
import com.example.diningroommanager.mapping.Token;
import com.example.diningroommanager.mapping.Usuario;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import unirest.shaded.com.google.gson.Gson;

public class MainScreen extends AppCompatActivity {

    Usuario user;
    ArrayList <String> comedoresId = new ArrayList <String> ();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        HttpResponse res = Unirest.get("http://diningroommanager.live:8000/users/me")
                .header("Authorization", "Bearer " + Session.getInstance().tk.getAccessToken())
                .header("accept", "Application/json")
                .asJson();
        user = new Gson().fromJson(res.getBody().toString(), Usuario.class);
        TextView tvMainScreen = findViewById(R.id.tvBienvenida);
        tvMainScreen.setText(String.format(tvMainScreen.getText().toString(), user.getNombre()));

        HttpResponse res2 = Unirest.get("http://diningroommanager.live:8000/comedores")
                .header("Authorization", "Bearer " + Session.getInstance().tk.getAccessToken())
                .header("accept", "Application/json")
                .asJson();

        ArrayList <Comedor> comedores = new Gson().fromJson(res2.getBody().toString(), new TypeToken<ArrayList<Comedor>>(){}.getType());
        comedores.forEach(comedor -> {
            comedoresId.add("Comedor: " + comedor.getId());
        });

        // String[] comedorId = new String[comedoresId.size()];
        // comedoresId.toArray(comedorId);

        Spinner spinner = (Spinner) findViewById(R.id.spinnerComedor);
        ArrayAdapter <String> adapter = new ArrayAdapter <String> (this, android.R.layout.simple_spinner_dropdown_item, comedoresId);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menumoreoptions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menumoreoptionsitem1:
                Intent it = new Intent(getApplicationContext(), Reservas.class);
                startActivity(it);
                return true;
            case R.id.menumoreoptionsitem2:
                if (user.getIsAdmin() == 1) {
                    Intent it2 = new Intent(getApplicationContext(), CrearMesa.class);
                    startActivity(it2);
                } else {
                    Toast.makeText(getApplicationContext(), "Solo los administradores pueden realizar esa accíon", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menudeletereserva, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menudeletereservaitem1:
                Toast.makeText(getApplicationContext(), "Funciona, pero hay que hacer el code crack", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

}