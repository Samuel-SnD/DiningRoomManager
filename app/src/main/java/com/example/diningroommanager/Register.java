package com.example.diningroommanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diningroommanager.mapping.Token;
import com.example.diningroommanager.mapping.UsuarioCreate;
import com.google.gson.Gson;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        EditText email = findViewById(R.id.etemail2);
        EditText password = findViewById(R.id.etpasswd2);
        EditText usuario = findViewById(R.id.etnombre);
        EditText apellidos = findViewById(R.id.etapellidos);
        Button btnRegister = findViewById(R.id.btnRegister2);
        Button btnVolver = findViewById(R.id.btnVolver);

        btnRegister.setOnClickListener(v -> {
            try {
                UsuarioCreate user = new UsuarioCreate(usuario.getText().toString(), apellidos.getText().toString(),
                        email.getText().toString(), password.getText().toString());

                HttpResponse res = Unirest.post("http://diningroommanager.live:8000/users")
                        .header("accept", "application/json")
                        .header("Content-Type","application/json")
                        .body(new Gson().toJson(user))
                        .asJson();
                if (res.getStatus() == 200) {
                    Toast.makeText(getApplicationContext(), "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Ese usuario ya ha sido registrado", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            };
        });

        btnVolver.setOnClickListener(v -> {
            Intent it = new Intent(getApplicationContext(), LogIn.class);
            startActivity(it);
        });
    }
}