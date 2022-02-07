package com.example.examen2prom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class Parte1 extends AppCompatActivity {

    private SQLiteDatabase db;
    private Spinner spinClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parte1);

        // Conexion con la BBDD
        ClientesSQLiteHelper csdbh = new ClientesSQLiteHelper(this, "DBClientes", null, 1);
        db = csdbh.getWritableDatabase();

        // Declaracion de Views
        spinClientes = findViewById(R.id.spinClientes);
        Button crearCliente = findViewById(R.id.crearCliente);
        Button modificarCliente = findViewById(R.id.modificarCliente);
        Button crearFactura = findViewById(R.id.crearFactura);
        Button consultarFacturas = findViewById(R.id.consultarFacturas);
        TextView facturas = findViewById(R.id.facturas);

        cargarSpinner();

        // Metodo onClick del boton crearCliente, llama a la actividad para crear el cliente
        crearCliente.setOnClickListener(view -> {
            Intent intent = new Intent(Parte1.this, CrearCliente.class);
            startActivity(intent);
        });

        // Metodo onClick del boton modificarCliente, abre un dialogo para introducir un DNI.
        // Si existe el dni, abre la actividad para modificar el cliente, si no, saca otro dialogo
        modificarCliente.setOnClickListener(view -> {
            final String[] dniABuscar = {""};

            // Crear el dialogo
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.insertar_dni);

            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);

            // Boton "editar" del dialogo
            builder.setPositiveButton(R.string.editar, (dialogInterface, i) -> {
                // Buscar el cliente
                dniABuscar[0] = input.getText().toString();
                String consulta = "SELECT * from Clientes where dni = ?";
                @SuppressLint("Recycle") Cursor c = db.rawQuery(consulta, dniABuscar);

                if(c.moveToFirst()) {
                    // Si exsite el cliente, llamar a la actividad de modificar pasandole los datos
                    String dni = String.valueOf(c.getInt(0));
                    String nombre = c.getString(1);
                    String direccion = c.getString(2);
                    String telefono = c.getString(3);

                    Intent intent = new Intent(Parte1.this, ModificarCliente.class);
                    intent.putExtra("dni",dni);
                    intent.putExtra("nombre", nombre);
                    intent.putExtra("direccion", direccion);
                    intent.putExtra("telefono", telefono);
                    startActivity(intent);
                }
                else {
                    // Si no existe, sacar otro dialogo
                    new android.app.AlertDialog.Builder(Parte1.this)
                            .setTitle(R.string.error)
                            .setMessage(R.string.cliente_no)
                            .setCancelable(false)
                            .setPositiveButton(R.string.ok, (dialogInterface2, j) -> dialogInterface2.cancel())
                            .show();
                }
            });

            // Boton cancelar del dialogo, cierra el dialogo
            builder.setNegativeButton(R.string.cancelar, (dialogInterface, i) -> dialogInterface.cancel());

            builder.show();
            cargarSpinner();
        });

        // Metodo onClick del boton crearFactura, abre la actividad para crear una factura
        crearFactura.setOnClickListener(view -> {
            Intent intent = new Intent(Parte1.this, CrearFactura.class);
            startActivity(intent);
        });

        // Metodo onClick del boton consultarFacturas, coge el DNI del spinner y muestra las facturas
        consultarFacturas.setOnClickListener(view -> {
            facturas.setText("");
            String dni = (String) spinClientes.getSelectedItem();
            String consulta = "SELECT num, clientes.dni, nombre, concepto, valor " +
                    "FROM Facturas, Clientes " +
                    "WHERE Facturas.dni = Clientes.dni AND Facturas.dni = ?";

            @SuppressLint("Recycle") Cursor c = db.rawQuery(consulta, new String[] {dni});
            while(c.moveToNext()) {
                String linea = c.getInt(0) +
                        "\t\t\t" + c.getInt(1) +
                        "\t\t\t" + c.getString(2) +
                        "\t\t\t" + c.getString(3) +
                        "\t\t\t" + c.getDouble(4) + "€";
                facturas.append(linea + "\n");
            }
        });
    }

    private void cargarSpinner() {
        // Crea un arrayList de Strings con los DNI de todos los clientes y lo añade como adapter
        ArrayList<String> lista = new ArrayList<>();
        String consulta1 = "SELECT dni from Clientes";
        @SuppressLint("Recycle") Cursor c1 = db.rawQuery(consulta1, null);
        while(c1.moveToNext()) {
            lista.add(String.valueOf(c1.getInt(0)));
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lista);
        spinClientes.setAdapter(arrayAdapter);
    }
}