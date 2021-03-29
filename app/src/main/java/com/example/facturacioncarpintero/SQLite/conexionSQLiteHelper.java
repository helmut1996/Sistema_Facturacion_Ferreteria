package com.example.facturacioncarpintero.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.facturacioncarpintero.SQLite.ulilidades.utilidades;
import com.example.facturacioncarpintero.SQLite.ulilidades.utilidadesFact;

public class conexionSQLiteHelper extends SQLiteOpenHelper {

    /*variable para crear tabla en sqlite*/

    public conexionSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(utilidades.CREAR_TABLA_PRODUCTO);
            db.execSQL( utilidadesFact.CREAR_TABLA_RECIBO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS producto");
        db.execSQL("DROP TABLE IF EXISTS recibo");
        onCreate(db);

    }
}
