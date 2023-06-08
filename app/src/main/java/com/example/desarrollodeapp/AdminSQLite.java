package com.example.desarrollodeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLite extends SQLiteOpenHelper {

    public AdminSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table usuario(documento integer primary key, nombre String, correo String unique, contrasenia String)");
        db.execSQL("create table pacientes( nombre String, fechaNacimiento text, sexo String)");
        db.execSQL("create table vacunas( nombre String, fechaVacuna String, fechaProx String)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists usuario");
        db.execSQL("drop table if exists pacientes");
        db.execSQL("drop table if exists vacunas");
        onCreate(db);
    }
}
