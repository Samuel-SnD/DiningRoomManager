package com.example.diningroommanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import com.example.diningroommanager.mapping.Menu;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import unirest.shaded.com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    Spinner spinner;
    ListView listView;
    ArrayList<String> menusId = new ArrayList <> ();
    int comedor;
    ArrayList <Menu> arrMenus = new ArrayList <Menu> ();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Button btnVolver = findViewById(R.id.btnVolverMenu);
        spinner = findViewById(R.id.spinnerMenu);
        Intent it = getIntent();
        comedor = Integer.parseInt(it.getStringExtra("comedor"));

        HttpResponse res = Unirest.get("http://diningroommanager.live:8000/comedor/" + comedor + "/menus")
                .header("Authorization", "Bearer " + Session.getInstance().tk.getAccessToken())
                .header("accept", "Application/json")
                .asJson();

        ArrayList <Menu> menus = new Gson().fromJson(res.getBody().toString(), new TypeToken<ArrayList<Menu>>(){}.getType());
        menus.forEach(menu -> {
            menusId.add("Menu: " + menu.getId());
        });

        ArrayAdapter<String> adapter = new ArrayAdapter <String> (this, android.R.layout.simple_spinner_dropdown_item, menusId);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        listView = findViewById(R.id.lvMenu);
        ListAdapter3 lAdapter = new ListAdapter3(getApplicationContext(), menus);
        listView.setAdapter(lAdapter);

        updateArrayMenus();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listView.invalidateViews();
                updateArrayMenus();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateArrayMenus () {
        HttpResponse res2 = Unirest.get("http://diningroommanager.live:8000/comedor/" + comedor + "/menus")
                .header("Authorization", "Bearer " + Session.getInstance().tk.getAccessToken())
                .header("accept", "Application/json")
                .asJson();

        ArrayList <Menu> arrMenus2 = new Gson().fromJson(res2.getBody().toString(), new TypeToken<ArrayList<Menu>>(){}.getType());
        arrMenus.clear();
        arrMenus2.forEach(menu -> {
            arrMenus.add(menu);
        });
    }
}