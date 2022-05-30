package com.example.diningroommanager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.diningroommanager.mapping.Comedor;
import com.example.diningroommanager.mapping.Mesa;
import com.example.diningroommanager.mapping.Usuario;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import unirest.shaded.com.google.gson.Gson;

public class MainScreen extends AppCompatActivity {

    SharedPreferences sharedPref;
    static Comedor currentComedor = new Comedor();
    static Usuario user;
    ArrayList <String> comedoresId = new ArrayList <String> ();
    static ArrayList <Mesa> arrMesas = new ArrayList <Mesa> ();
    static Spinner spinner;
    static ListView lvmainScreen;
    Boolean isNew = true;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        HttpResponse res = Unirest.get("http://diningroommanager.live:8000/users/me")
                .header("Authorization", "Bearer " + sharedPref.getString("Token", ""))
                .header("accept", "Application/json")
                .asJson();
        user = new Gson().fromJson(res.getBody().toString(), Usuario.class);
        Session.getInstance().user = user;

        HttpResponse res2 = Unirest.get("http://diningroommanager.live:8000/comedores")
                .header("Authorization", "Bearer " + sharedPref.getString("Token", ""))
                .header("accept", "Application/json")
                .asJson();

        ArrayList <Comedor> comedores = new Gson().fromJson(res2.getBody().toString(), new TypeToken<ArrayList<Comedor>>(){}.getType());
        comedores.forEach(comedor -> {
            comedoresId.add("Comedor: " + comedor.getId());
        });

        spinner = findViewById(R.id.spinnerComedor);
        ArrayAdapter <String> adapter = new ArrayAdapter <String> (this, android.R.layout.simple_spinner_dropdown_item, comedoresId);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        lvmainScreen = findViewById(R.id.lvmainScreen);
        ListAdapter lAdapter = new ListAdapter(getApplicationContext(), arrMesas);
        lvmainScreen.setAdapter(lAdapter);
        registerForContextMenu(lvmainScreen);

        updateArrayMesas();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lvmainScreen.invalidateViews();
                updateArrayMesas();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), MenuActivity.class);
                it.putExtra("comedor", spinner.getSelectedItem().toString().substring(9));
                startActivity(it);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateArrayMesas () {
        currentComedor.setId(Integer.parseInt(spinner.getSelectedItem().toString().substring(9)));

        HttpResponse res3 = Unirest.get("http://diningroommanager.live:8000/mesas/")
                .header("Authorization", "Bearer " + sharedPref.getString("Token", ""))
                .header("accept", "Application/json")
                .queryString("comedor_id", currentComedor.getId())
                .asJson();

        ArrayList <Mesa> mesas = new Gson().fromJson(res3.getBody().toString(), new TypeToken<ArrayList<Mesa>>(){}.getType());
        arrMesas.clear();
        mesas.forEach(mesa -> {
            arrMesas.add(mesa);
        });
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
        inflater.inflate(R.menu.menucreatereserva, menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int listPosition = info.position;
        switch (item.getItemId()) {
            case R.id.menucreatereservaitem1:
                Intent it = new Intent(getApplicationContext(), CrearReserva.class);
                it.putExtra("array", arrMesas);
                it.putExtra("posicion", listPosition);
                startActivity(it);
                return true;
            case R.id.menucreatereservaitem2:
                if (user.getIsAdmin() == 1) {
                    int mesa = arrMesas.get(listPosition).getId();
                    HttpResponse res = Unirest.delete("http://diningroommanager.live:8000/mesas/" + mesa)
                            .header("Authorization", "Bearer " + sharedPref.getString("Token", ""))
                            .header("accept", "Application/json")
                            .asJson();
                    if (res.getStatus() == 409) {
                        Toast.makeText(getApplicationContext(), "No se pueden eliminar mesas que contengan reservas", Toast.LENGTH_SHORT).show();
                    } else if (res.getStatus() == 200) {
                        Toast.makeText(getApplicationContext(), "Mesa eliminada", Toast.LENGTH_SHORT).show();
                        lvmainScreen.invalidateViews();
                        updateArrayMesas();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Solo los administradores pueden realizar esa accíon", Toast.LENGTH_SHORT).show();
                } return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onResume() {
        super.onResume();
        if (!isNew) {
            lvmainScreen.invalidateViews();
            updateArrayMesas();
            finish();
            Intent it = new Intent(getApplicationContext(), MainScreen.class);
            startActivity(it);
        }
        isNew = false;
    }
}