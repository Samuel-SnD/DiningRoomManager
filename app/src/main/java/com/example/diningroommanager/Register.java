package com.example.diningroommanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.mindrot.jbcrypt.BCrypt;
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
        CheckBox cbCredentials = findViewById(R.id.cbacceptcredentials);
        TextView tvCredentials = findViewById(R.id.tvacceptcredentials);

        String cb = "He leído y acepto los términos y condiciones";
        SpannableString ss = new SpannableString(cb);
        ClickableSpan cs = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                MessageDialogFragment mensaje = new MessageDialogFragment();
                mensaje.show(getSupportFragmentManager(), "AlertDialog");
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(cs,22, 44, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvCredentials.setText(ss);
        tvCredentials.setMovementMethod(LinkMovementMethod.getInstance());

        btnRegister.setOnClickListener(v -> {
            try {
                if (cbCredentials.isChecked()) {
                    UsuarioCreate user = new UsuarioCreate(usuario.getText().toString(), apellidos.getText().toString(),
                            email.getText().toString(), BCrypt.hashpw(password.getText().toString(), BCrypt.gensalt()));

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
                } else {
                    Toast.makeText(getApplicationContext(), "Debes aceptar los términos y condiciones", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            };
        });
    }
}