package com.example.desarrollodeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class activity_esquema extends AppCompatActivity {

    EditText nombre, fechaVacuna, fechaProx;

    Button guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esquema);

        nombre = findViewById(R.id.editNV);
        fechaVacuna = findViewById(R.id.editFechaV);
        fechaProx = findViewById(R.id.editProximaV);

        guardar = findViewById(R.id.btnVacuna);


        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminSQLite admin = new AdminSQLite(getApplicationContext(), "Administrativo", null, 2);
                SQLiteDatabase db = admin.getWritableDatabase();

                if (nombre.getText().toString().equals("")) {
                    nombre.setError(("Nombre de la Vacuna requerido"));
                    return;
                }

                if (fechaVacuna.getText().toString().equals("")) {
                    fechaVacuna.setError("Fecha de la Vacuna requerido");
                    return;
                }
                if (fechaProx.getText().toString().equals("")) {
                    fechaProx.setError("Fecha próxima Vacuna requerido");
                    return;
                }

                String nomVac = nombre.getText().toString();
                String fechaVac = fechaVacuna.getText().toString();
                String fechaP = fechaProx.getText().toString();

                ContentValues datos = new ContentValues();
                datos.put("nombreVacuna", nomVac);
                datos.put("fechaVacuna", fechaVac);
                datos.put("fechaProx", fechaP);

                try {
                    db.insert("vacunas", null, datos);
                    db.close();

                    nombre.setText("");
                    fechaVacuna.setText("");
                    fechaProx.setText("");
                    Toast.makeText(activity_esquema.this, "Vacunas registradas con éxito", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Toast.makeText(activity_esquema.this, "Error registrando vacuna", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}