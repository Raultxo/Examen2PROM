package com.example.examen2prom;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class CrearCliente extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cliente);

        // Conexion con la BBDD
        ClientesSQLiteHelper csdbh = new ClientesSQLiteHelper(this, "DBClientes", null, 1);
        SQLiteDatabase db = csdbh.getWritableDatabase();

        // Declaracion de Views
        EditText dni = findViewById(R.id.dni);
        EditText nombre = findViewById(R.id.nombre);
        EditText direccion = findViewById(R.id.direccion);
        EditText telefono = findViewById(R.id.telefono);
        Button crear = findViewById(R.id.crear);

        // Metodo onClick del boton crear, añade el cliente a la BBDD
        crear.setOnClickListener(view -> {
            String consulta = "INSERT INTO Clientes " +
                    "(dni, nombre, direccion, telefono) " +
                    "VALUES ('"+ dni.getText().toString() + "', '" +
                    nombre.getText().toString() + "', '" +
                    direccion.getText().toString() + "', '" +
                    telefono.getText().toString() + "')";
            db.execSQL(consulta);
            db.close();
            finish();
        });


    }
}