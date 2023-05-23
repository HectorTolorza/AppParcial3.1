package com.example.agendatuuduu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agendatuuduu.AgregarNota.Agregar_Nota;
import com.example.agendatuuduu.ListarNotas.Listar_Notas;
import com.example.agendatuuduu.NotasArchivadas.Notas_Archivadas;
import com.example.agendatuuduu.Perfil.Perfil_Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuPrincipal extends AppCompatActivity {

    Button AgregarNotas, ListarNotas, Archivados, Perfil, AcercaDe, CerrarSesion;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    TextView UidPrincipal,NombresPrincipal, CorreoPrincipal;
    ProgressBar progressBarDatos;

    DatabaseReference Usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);


        NombresPrincipal = findViewById(R.id.NombresPrincipal);
        UidPrincipal = findViewById(R.id.UidPrincipal);
        CorreoPrincipal = findViewById(R.id.CorrePrincipal);
        progressBarDatos = findViewById(R.id.progressBarDatos);
        Usuarios = FirebaseDatabase.getInstance().getReference("Usuarios");

        AgregarNotas = findViewById(R.id.AgregarNotas);
        ListarNotas = findViewById(R.id.ListarNotas);
        Archivados = findViewById(R.id.Archivados);
        Perfil = findViewById(R.id.Perfil);
        AcercaDe = findViewById(R.id.AcercaDe);
        CerrarSesion = findViewById(R.id.CerrarSesion);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        AgregarNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uid_user = UidPrincipal.getText().toString();
                String corre_usuario = CorreoPrincipal.getText().toString();

                Intent intent = new Intent(MenuPrincipal.this, Agregar_Nota.class);
                intent.putExtra("Uid", uid_user);
                intent.putExtra("Correo", corre_usuario);
                startActivity(intent);
            }
        });

        ListarNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuPrincipal.this, Listar_Notas.class));
                Toast.makeText(MenuPrincipal.this, "Listar Notas", Toast.LENGTH_SHORT).show();
            }
        });

        Archivados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuPrincipal.this, Notas_Archivadas.class));
                Toast.makeText(MenuPrincipal.this, "Nota Archivada", Toast.LENGTH_SHORT).show();
            }
        });

        Perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuPrincipal.this, Perfil_Usuario.class));
                Toast.makeText(MenuPrincipal.this, "Perfil", Toast.LENGTH_SHORT).show();
            }
        });

        AcercaDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MenuPrincipal.this, "Acerca De", Toast.LENGTH_SHORT).show();
            }
        });



        CerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SalirApp();
            }
        });
    }

    @Override
    protected void onStart() {
        ComprobarInicioSesion();
        super.onStart();
    }


    private void ComprobarInicioSesion(){
        if (user!=null){
            CargaDeDatos();
        }else {
            startActivity(new Intent(MenuPrincipal.this,MainActivity.class));
            finish();
        }
    }


    private void CargaDeDatos(){
        Usuarios.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                   progressBarDatos.setVisibility(View.GONE);

                   UidPrincipal.setVisibility(View.VISIBLE);
                   NombresPrincipal.setVisibility(View.VISIBLE);
                   CorreoPrincipal.setVisibility(View.VISIBLE);


                   String uid = ""+snapshot.child("uid").getValue();
                   String nombres = ""+snapshot.child("nombres").getValue();
                   String correo = ""+snapshot.child("correo").getValue();


                   UidPrincipal.setText(uid);
                   NombresPrincipal.setText(nombres);
                   CorreoPrincipal.setText(correo);


                   AgregarNotas.setEnabled(true);
                   ListarNotas.setEnabled(true);
                   Archivados.setEnabled(true);
                   Perfil.setEnabled(true);
                   AcercaDe.setEnabled(true);
                   CerrarSesion.setEnabled(true);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void SalirApp() {
        firebaseAuth.signOut();
        startActivity(new Intent(MenuPrincipal.this, MainActivity.class));
        Toast.makeText(this, "Cerraste sesion exitosamente",Toast.LENGTH_SHORT).show();
    }
}