package com.example.examen2prom;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Declaracion de Views
        Button parte1 = findViewById(R.id.parte1);
        Button parte2 = findViewById(R.id.parte2);
        Button salir = findViewById(R.id.salir);

        // Metodo onClick del boton parte1, abre la parte 1 del examen
        parte1.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Parte1.class);
            startActivity(intent);
        });

        // Metodo onClick del boton parte2, abre la parte 2 del examen
        parte2.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Parte2.class);
            startActivity(intent);
        });

        // Metodo onClick del boton salir, abre un dialogo
        salir.setOnClickListener(view -> new AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.adios)
                .setMessage(R.string.mensajeAdios)
                .setCancelable(false)
                .setPositiveButton(R.string.salir, (dialogInterface, i) -> finish())
                .show());
    }
}