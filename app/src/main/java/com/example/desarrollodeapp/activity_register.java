package com.example.desarrollodeapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class activity_register extends Activity {

    EditText documento, nombre, correo, contrasenia;

    Button guardar, consultar, editar, eliminar, regresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        documento = findViewById(R.id.editDoc);
        nombre = findViewById(R.id.editName);
        correo = findViewById(R.id.editEmail);
        contrasenia = findViewById(R.id.editPassword);

        guardar = findViewById(R.id.btn_guardar);
        consultar = findViewById(R.id.btn_consultar);
        editar = findViewById(R.id.btn_editar);
        eliminar = findViewById(R.id.btn_eliminar);
        regresar = findViewById(R.id.btn_regresar);


        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminSQLite admin = new AdminSQLite(getApplicationContext(), "Administrativo", null,2);
                SQLiteDatabase db = admin.getWritableDatabase();

                if(documento.getText().toString().equals("")){
                    documento.setError(("Documento requerido"));
                    return;
                }

                if(nombre.getText().toString().equals("")){
                    nombre.setError("Nombre requerido");
                    return;
                }
                if(correo.getText().toString().equals("")) {
                    correo.setError("Correo requerido");
                    return;
                }
                if(contrasenia.getText().toString().equals("")){
                    contrasenia.setError("Contraseña requerida");
                }

                int doc = Integer.parseInt(documento.getText().toString());
                String nom = nombre.getText().toString();
                String email = correo.getText().toString();
                String password = contrasenia.getText().toString();

                ContentValues datos = new ContentValues();
                datos.put("documento", doc);
                datos.put("nombre", nom);
                datos.put("correo", email);
                datos.put("contrasenia", password);

                try{
                    db.insert("usuario",null,datos);
                    db.close();

                    documento.setText("");
                    nombre.setText("");
                    correo.setText("");
                    contrasenia.setText("");
                    Toast.makeText(activity_register.this, "Usuario creado con éxito", Toast.LENGTH_SHORT).show();
                }catch (Exception e) {
                    Toast.makeText(activity_register.this, "Error creando usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminSQLite admin = new AdminSQLite(getApplicationContext(), "Administrativo", null,1);
                SQLiteDatabase db = admin.getWritableDatabase();


                try{
                    String email = correo.getText().toString();
                    Cursor fila = db.rawQuery("select * from usuario where correo = '"+ email +"'", null);
                    if(fila.moveToFirst()) {
                        documento.setText(fila.getString(0));
                        nombre.setText(fila.getString(1));
                        correo.setText(fila.getString(2));
                    }else {
                        Toast.makeText(activity_register.this, "Usuario no existe", Toast.LENGTH_SHORT).show();
                    }
                    db.close();
                } catch (Exception e) {
                    Toast.makeText(activity_register.this, "Error al consultar", Toast.LENGTH_SHORT).show();
                }
            }
        });

       editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminSQLite admin = new AdminSQLite(getApplicationContext(), "Administrativo", null,1);
                SQLiteDatabase db = admin.getWritableDatabase();

                if(documento.getText().toString().equals("")){
                    documento.setError(("Documento requerido"));
                    return;
                }

                if(nombre.getText().toString().equals("")){
                    nombre.setError("Nombre requerido");
                    return;
                }
                if(correo.getText().toString().equals("")) {
                    correo.setError("Correo requerido");
                    return;
                }
                if(contrasenia.getText().toString().equals("")){
                    contrasenia.setError("Contraseña requerida");
                }
                int doc = Integer.parseInt(documento.getText().toString());
                String nom = nombre.getText().toString();
                String email = correo.getText().toString();
                String password = contrasenia.getText().toString();

                ContentValues updateDatos = new ContentValues();
                updateDatos.put("nombre", nom);
                updateDatos.put("correo",email);
                updateDatos.put("documento",doc);
                updateDatos.put("contrasenia",password);

                nombre.setText("");
                correo.setText("");
                documento.setText("");
                contrasenia.setText("");
                try{
                    int c = db.update("usuario", updateDatos, "documento=" +doc, null);
                    if(c>0) {
                        Toast.makeText(activity_register.this, "Usuario actualizado con éxito", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(activity_register.this, "Usuario no existe", Toast.LENGTH_SHORT).show();
                    }
                    db.close();
                }catch (Exception e ){
                    Toast.makeText(activity_register.this, "Error consultando", Toast.LENGTH_SHORT).show();
                }
            }
        });
       eliminar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               AdminSQLite admin = new AdminSQLite(getApplicationContext(), "Administrativo", null,1);
               SQLiteDatabase db = admin.getWritableDatabase();

               try {
                   int doc = Integer.parseInt(documento.getText().toString());
                    int c = db.delete("usuario", "documento= "+doc,null);
                    if(c>0){
                        Toast.makeText(activity_register.this, "Usuario eliminado con éxito", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(activity_register.this, "Usuario no existe", Toast.LENGTH_SHORT).show();
                    }
                    documento.setText("");
                    nombre.setText("");
                    correo.setText("");
                    contrasenia.setText("");
                    db.close();
               }catch (Exception e){
                   Toast.makeText(activity_register.this, "Error eliminando", Toast.LENGTH_SHORT).show();
               }
           }
       });

       regresar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent login = new Intent(activity_register.this, activity_login.class);
               activity_register.this.startActivity(login);
               finish();
           }
       });

    }
}