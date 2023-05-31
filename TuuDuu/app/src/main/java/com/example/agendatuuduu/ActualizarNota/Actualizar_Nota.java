package com.example.agendatuuduu.ActualizarNota;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.agendatuuduu.R;

public class Actualizar_Nota extends AppCompatActivity {

    TextView Uid_Usuario_A, Correo_Usuario_A, Fecha_registro_A, Fecha_A, Estado_A, Id_Nota_A;
    EditText Titulo_A, Descripcion_A;
    Button Btn_Calendario_A;

    String id_nota_R;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_nota);
        InicializarVistas();
        RecuperarDatos();
        SetearDatos();
    }

    private void InicializarVistas(){
        Id_Nota_A = findViewById(R.id.Id_Nota_A);
    }

    private void RecuperarDatos(){
        Bundle intent = getIntent().getExtras();

        id_nota_R = intent.getString("id_nota");
    }

    private void SetearDatos(){

        Id_Nota_A.setText(id_nota_R);


    }
}