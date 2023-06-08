package com.example.desarrollodeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class activity_login extends Activity {

    EditText correo, contrasenia;

    Button ingresar, registrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        correo = findViewById(R.id.editTextTextEmailAddress);
        contrasenia = findViewById(R.id.editTextTextPassword);
        ingresar = findViewById(R.id.btn_login);
        registrarse = findViewById(R.id.btn_register);

    ingresar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AdminSQLite admin = new AdminSQLite(getApplicationContext(), "Administrativo", null,2);
            SQLiteDatabase db = admin.getWritableDatabase();


            try{
                String email = correo.getText().toString();
                String password = contrasenia.getText().toString();


                if(!email.isEmpty()) {
                    Cursor fila = db.rawQuery("select * from usuario where correo = '" + email + "'", null);

                    if (fila.moveToFirst()) {
                        if (email.equals(fila.getString(2)) && password.equals(fila.getString(3))) {
                            Intent intent = new Intent(activity_login.this, activity_home.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(activity_login.this, "Usuario o contraseña erradas", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        correo.setText("");
                        contrasenia.setText("");
                        Toast.makeText(activity_login.this, "Usuario no existe", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(activity_login.this, "Ingrese un correo", Toast.LENGTH_SHORT).show();
                }
                db.close();
            } catch (Exception e) {
                Toast.makeText(activity_login.this, "Error al ingresar", Toast.LENGTH_SHORT).show();
            }
        }
    });
    registrarse.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent registrar = new Intent(activity_login.this, activity_register.class);
            startActivity(registrar);
            finish();
        }
    });
    }
}