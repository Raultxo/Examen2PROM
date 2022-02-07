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

        ClientesSQLiteHelper csdbh = new ClientesSQLiteHelper(this, "DBClientes", null, 1);
        SQLiteDatabase db = csdbh.getWritableDatabase();

        EditText dni = findViewById(R.id.dni);
        dni.setText(getIntent().getExtras().getString("dni"));
        dni.setEnabled(false);

        EditText nombre = findViewById(R.id.nombre);
        nombre.setText(getIntent().getExtras().getString("nombre"));
        nombre.setEnabled(false);

        EditText direccion = findViewById(R.id.direccion);
        direccion.setText(getIntent().getExtras().getString("direccion"));

        EditText telefono = findViewById(R.id.telefono);
        telefono.setText(getIntent().getExtras().getString("telefono"));

        Button editar = findViewById(R.id.editar);

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