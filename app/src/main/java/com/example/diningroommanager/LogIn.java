package com.example.diningroommanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diningroommanager.mapping.Token;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class LogIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        EditText email = findViewById(R.id.etemail);
        EditText password = findViewById(R.id.etpasswd);
        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> {
            try {
                HttpResponse res = Unirest.post("http://diningroommanager.live:8000/token")
                        .header("accept", "Application/json")
                        .field("username", email.getText())
                        .field("password", password.getText())
                        .asJson();
                if (res.getStatus() == 200) {
                    Token tk = new Gson().fromJson(res.getBody().toString(), Token.class);
                    Intent it = new Intent(getApplicationContext(), MainScreen.class);
                    it.putExtra("Token", tk);
                    startActivity(it);
                } else {
                    Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            };
        });

    }
}