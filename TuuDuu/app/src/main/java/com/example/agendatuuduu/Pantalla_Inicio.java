package com.example.agendatuuduu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Pantalla_Inicio extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicio);

        firebaseAuth = FirebaseAuth.getInstance();

        int tiempo = 3000;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                VerificarUsuario();
            }
        }, tiempo);
    }

    private void VerificarUsuario(){

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser == null){
            startActivity(new Intent(Pantalla_Inicio.this, MainActivity.class));
            finish();
        }else {
            startActivity(new Intent(Pantalla_Inicio.this, MenuPrincipal.class));
            finish();
        }
    }
}