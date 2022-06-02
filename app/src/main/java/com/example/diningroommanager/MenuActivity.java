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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.diningroommanager.mapping.Menu;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import unirest.shaded.com.google.gson.Gson;

import com.example.diningroommanager.mapping.Usuario;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    // Declaración de variables
    Usuario user;
    ListView listView;
    int comedor;
    ArrayList <Menu> arrMenus = new ArrayList <Menu> ();
    SharedPreferences sharedPref;
    Boolean isNew = true;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Necesario para no recibir una Network exception
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Recupero el usuario, el comedor y las sharedPreferences
        user = Utils.getUsuario(this);
        Intent it = getIntent();
        comedor = Integer.parseInt(it.getStringExtra("comedor"));
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        // Realizo una petición a la API para recuperar una lsita de los menus
        HttpResponse res = Unirest.get("http://diningroommanager.live:8000/comedor/" + comedor + "/menus")
                .header("Authorization", "Bearer " + sharedPref.getString("Token", ""))
                .header("accept", "Application/json")
                .asJson();

        ArrayList <Menu> menus = new Gson().fromJson(res.getBody().toString(), new TypeToken<ArrayList<Menu>>(){}.getType());

        // Creo un adapter para la listView y lo establezco, también la registro para el contextMenu
        listView = findViewById(R.id.lvMenu);
        ListAdapter3 lAdapter = new ListAdapter3(this, menus);
        listView.setAdapter(lAdapter);
        registerForContextMenu(listView);

        // Actualizo el array de menus con los que existan en ese momento
        updateArrayMenus();
    }

    // Método que actualiza los menus actuales realizando una petición a la API
    // También parseo las strings que recibo a jsons para poder agregarlo a la base de datos
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateArrayMenus () {
        HttpResponse res2 = Unirest.get("http://diningroommanager.live:8000/comedor/" + comedor + "/menus")
                .header("Authorization", "Bearer " + sharedPref.getString("Token", ""))
                .header("accept", "Application/json")
                .asJson();

        ArrayList <Menu> arrMenus2 = new Gson().fromJson(res2.getBody().toString(), new TypeToken<ArrayList<Menu>>(){}.getType());
        arrMenus.clear();
        arrMenus2.forEach(menu -> {
            ArrayList <String> platos = new Gson().fromJson(menu.getPlatos(), new TypeToken<ArrayList<String>>(){}.getType());
            ArrayList <String> bebidas = new Gson().fromJson(menu.getBebidas(), new TypeToken<ArrayList<String>>(){}.getType());
            String platospretty = "";
            String bebidaspretty = "";
            for (String plato : platos) {
                platospretty += plato + ", ";
            }
            for (String bebida : bebidas) {
                bebidaspretty += bebida + ", ";
            }
            menu.setPlatos(platospretty.substring(0, platospretty.length() - 2));
            menu.setBebidas(bebidaspretty.substring(0, bebidaspretty.length() - 2));
            arrMenus.add(menu);
        });

        // Creo un adapter para la listView y lo establezco, también la registro para el contextMenu
        ListAdapter3 lAdapter = new ListAdapter3(this, arrMenus);
        listView.setAdapter(lAdapter);
        registerForContextMenu(listView);
    }

    // Estos dos métodos crean un menú de opciones
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menucreatemenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            // Si el usuario es administrador se hace un intent a la activity pàra crear menús
            case R.id.menucreatemenu1:
                if (user.getIsAdmin() == 1) {
                    Intent it = new Intent(getApplicationContext(), CrearMenu.class);
                    it.putExtra("comedor", comedor);
                    startActivity(it);
                } else {
                    Toast.makeText(getApplicationContext(), "Solo los administradores pueden realizar esa accíon", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Estos dos métodos crean un menú contextual
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menudeletemenu, menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int listPosition = info.position;
        switch (item.getItemId()) {
            // Si el usuario es administrador se hace una petición a la API para eliminar el menú
            // seleccionado y actualizar la listView
            case R.id.menudeletemenu1:
                if (user.getIsAdmin() == 1) {
                    int menu = arrMenus.get(listPosition).getId();
                    HttpResponse res = Unirest.delete("http://diningroommanager.live:8000/menus/" + menu)
                            .header("Authorization", "Bearer " + sharedPref.getString("Token", ""))
                            .header("accept", "application/json")
                            .asJson();
                    if (res.getStatus() == 200) {
                        Toast.makeText(getApplicationContext(), "Menu eliminado", Toast.LENGTH_SHORT).show();
                        listView.invalidateViews();
                        updateArrayMenus();
                    } else {
                        Toast.makeText(getApplicationContext(), "No se ha podido eliminar el menu", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Solo los administradores pueden realizar esa accíon", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    // En el resume hago otra vez una actualización de la listview en caso de que no sea la primera
    // vez que se abre la activity
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onResume() {
        super.onResume();
        if (!isNew) {
            listView.invalidateViews();
            updateArrayMenus();
            finish();
            Intent it = new Intent(getApplicationContext(), MenuActivity.class);
            it.putExtra("comedor", String.valueOf(comedor));
            startActivity(it);
        }
        isNew = false;
    }
}