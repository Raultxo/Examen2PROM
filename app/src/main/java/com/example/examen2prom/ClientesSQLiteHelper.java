package com.example.examen2prom;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ClientesSQLiteHelper extends SQLiteOpenHelper {

    // Creacion de la primera tabla
    String crearTabla1 =
            "CREATE TABLE Clientes (" +
            "dni INTEGER PRIMARY KEY," +
            "nombre TEXT," +
            "direccion TEXT," +
            "telefono TEXT)";

    // Creacion de la segunda tabla
    String crearTabla2 =
            "CREATE TABLE Facturas (" +
            "num INTEGER PRIMARY KEY," +
            "dni INTEGER," +
            "concepto TEXT," +
            "valor REAL," +
            "FOREIGN KEY (dni) REFERENCES Clientes(dni))";


    public ClientesSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(crearTabla1);
        db.execSQL(crearTabla2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
