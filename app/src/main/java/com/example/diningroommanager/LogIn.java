package com.example.diningroommanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

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

                }
            } catch (Exception e) {
                e.printStackTrace();
            };
        });

    }
}