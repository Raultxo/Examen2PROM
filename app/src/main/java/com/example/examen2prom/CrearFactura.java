package com.example.examen2prom;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CrearFactura extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_factura);

        // Conexion con la BBDD
        ClientesSQLiteHelper csdbh = new ClientesSQLiteHelper(this, "DBClientes", null, 1);
        SQLiteDatabase db = csdbh.getWritableDatabase();

        // Declaracion de Views
        EditText num = findViewById(R.id.num);
        EditText dni = findViewById(R.id.dni);
        EditText concepto = findViewById(R.id.concepto);
        EditText valor = findViewById(R.id.valor);
        Button crear = findViewById(R.id.crear);
        Button volver = findViewById(R.id.volver);

        // Metodo onClick del boton volver, vuelve a la ventana principal
        volver.setOnClickListener(view -> finish());

        // Metodo onClick del boton crear, busca si existe el dni, en caso de que exista, crea la factura
        // Si no existe, saca un dialog
        crear.setOnClickListener(view -> {
            String dniABuscar = dni.getText().toString();
            String consulta = "SELECT * from Clientes where dni = ?";
            @SuppressLint("Recycle") Cursor c = db.rawQuery(consulta, new String[] {dniABuscar});

            if(c.moveToFirst()) {

                if(num.getText().toString().length() == 0) {
                    new AlertDialog.Builder(CrearFactura.this)
                            .setTitle(R.string.error)
                            .setMessage(R.string.numero_factura)
                            .setCancelable(false)
                            .setPositiveButton(R.string.ok, (dialogInterface2, j) -> dialogInterface2.cancel())
                            .show();
                }
                else {
                    String consulta2 = "INSERT INTO Facturas " +
                            "(num, dni, concepto, valor) " +
                            "VALUES ("+ num.getText().toString() + ", " +
                            dni.getText().toString() + ", '" +
                            concepto.getText().toString() + "', " +
                            valor.getText().toString() + ")";
                    db.execSQL(consulta2);
                    db.close();
                    finish();
                }
            }
            else {
                new AlertDialog.Builder(CrearFactura.this)
                        .setTitle(R.string.error)
                        .setMessage(R.string.cliente_no)
                        .setCancelable(false)
                        .setPositiveButton(R.string.ok, (dialogInterface2, j) -> dialogInterface2.cancel())
                        .show();
            }
        });
    }
}