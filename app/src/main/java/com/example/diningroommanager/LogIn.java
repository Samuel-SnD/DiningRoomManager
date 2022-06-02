package com.example.diningroommanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diningroommanager.mapping.Token;
import com.google.gson.Gson;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class LogIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        // Necesario para no recibir una Network exception
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Declaración de variables
        EditText email = findViewById(R.id.etemail);
        EditText password = findViewById(R.id.etpasswd);
        Button btnLogin = findViewById(R.id.btnLogin);

        // Si se aprieta el botón de LogIn realiza una petición a la API con el usuario
        // Una vez accede mete el token en las sharedPreferences
        // Después de esto hace un intent a MainScreen
        btnLogin.setOnClickListener(v -> {
            try {
                HttpResponse res = Unirest.post("http://diningroommanager.live:8000/token")
                        .header("accept", "Application/json")
                        .field("username", email.getText().toString())
                        .field("password", password.getText().toString())
                        .asJson();
                if (res.getStatus() == 200) {
                    Token tk = new Gson().fromJson(res.getBody().toString(), Token.class);
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("Token", tk.getAccessToken());
                    editor.commit();
                    Intent it = new Intent(getApplicationContext(), MainScreen.class);
                    startActivity(it);
                } else {
                    Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            };
        });

        // Si se aprieta el botón de registro hace un intent a la activity Register
        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(v -> {
            Intent it = new Intent(getApplicationContext(), Register.class);
            startActivity(it);
        });
    }
}