package com.example.desarrollodeapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class activity_home extends Activity {

    EditText nombre, fechaNacimiento, sexo;

    Button guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        nombre = findViewById(R.id.editNameUsuario);
        fechaNacimiento = findViewById(R.id.editDateUsuario);
        sexo = findViewById(R.id.editSexoUsuario);

        guardar = findViewById(R.id.btnGuardarUsuario);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminSQLite admin = new AdminSQLite(getApplicationContext(), "Administrativo", null, 2);
                SQLiteDatabase db = admin.getWritableDatabase();

                if (nombre.getText().toString().equals("")) {
                    nombre.setError(("Nombre requerido"));
                    return;
                }

                if (fechaNacimiento.getText().toString().equals("")) {
                    fechaNacimiento.setError("Fecha de Nacimiento requerida");
                    return;
                }
                if (sexo.getText().toString().equals("")) {
                    sexo.setError("Sexo requerido");
                    return;
                }

                String nom = nombre.getText().toString();
                String fecha = fechaNacimiento.getText().toString();
                String sex = sexo.getText().toString();

                ContentValues datos = new ContentValues();
                datos.put("nombre", nom);
                datos.put("fechaNacimiento", fecha);
                datos.put("sexo", sex);

                try {
                    db.insert("pacientes", null, datos);
                    db.close();

                    nombre.setText("");
                    fechaNacimiento.setText("");
                    sexo.setText("");
                    Toast.makeText(activity_home.this, "paciente creado con éxito", Toast.LENGTH_SHORT).show();

                    Intent guardar = new Intent(activity_home.this, activity_esquema.class);
                    startActivity(guardar);
                    finish();
                } catch (Exception e) {
                    Toast.makeText(activity_home.this, "Error creando paciente", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}