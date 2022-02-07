package com.example.examen2prom;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ModificarCliente extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_cliente);

        // Conexion con la BBDD
        ClientesSQLiteHelper csdbh = new ClientesSQLiteHelper(this, "DBClientes", null, 1);
        SQLiteDatabase db = csdbh.getWritableDatabase();

        // Declaracion de Views
        Button volver = findViewById(R.id.volver);
        EditText dni = findViewById(R.id.dni);
        EditText nombre = findViewById(R.id.nombre);
        EditText direccion = findViewById(R.id.direccion);
        EditText telefono = findViewById(R.id.telefono);
        Button editar = findViewById(R.id.editar);

        // Cargar datos del bundle en los textView
        dni.setText(getIntent().getExtras().getString("dni"));
        dni.setEnabled(false);
        nombre.setText(getIntent().getExtras().getString("nombre"));
        nombre.setEnabled(false);
        direccion.setText(getIntent().getExtras().getString("direccion"));
        telefono.setText(getIntent().getExtras().getString("telefono"));

        // Metodo onClick del boton volver, vuelve a la ventana principal
        volver.setOnClickListener(view -> finish());

        // Metodo onClick del boton editar, modificar el cliente con los nuevos datos
        editar.setOnClickListener(view -> {
            String consulta = "UPDATE Clientes SET " +
                    "direccion = '" + direccion.getText().toString() + "'," +
                    "telefono = '" + telefono.getText().toString() + "' " +
                    "where dni = " + dni.getText().toString();
            db.execSQL(consulta);
            db.close();
            finish();
        });
    }
}