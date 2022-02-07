package com.example.examen2prom;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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
        Button volver = findViewById(R.id.volver);

        // Metodo onClick del boton volver, vuelve a la ventana principal
        volver.setOnClickListener(view -> finish());

        // Metodo onClick del boton crear, añade el cliente a la BBDD
        crear.setOnClickListener(view -> {

            if(dni.getText().toString().length() != 8) {
                new AlertDialog.Builder(CrearCliente.this)
                        .setTitle(R.string.error)
                        .setMessage(R.string.el_dni)
                        .setCancelable(false)
                        .setPositiveButton(R.string.ok, (dialogInterface2, j) -> dialogInterface2.cancel())
                        .show();
            }
            else {
                String consulta = "INSERT INTO Clientes " +
                        "(dni, nombre, direccion, telefono) " +
                        "VALUES ('"+ dni.getText().toString() + "', '" +
                        nombre.getText().toString() + "', '" +
                        direccion.getText().toString() + "', '" +
                        telefono.getText().toString() + "')";
                db.execSQL(consulta);
                db.close();
                finish();
            }
        });


    }
}