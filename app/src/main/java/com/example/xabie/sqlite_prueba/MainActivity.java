package com.example.xabie.sqlite_prueba;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.ContentObservable;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Reader;

public class MainActivity extends Activity {

    private EditText etDni,etNumero, etNombre, etCiudad;
    Button btnMostrar = (Button) findViewById(R.id.btnMostrar);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etDni=(EditText) findViewById(R.id.etDni);
        etNumero=(EditText) findViewById(R.id.etTelefono);
        etNombre=(EditText) findViewById(R.id.etNombre);
        etCiudad=(EditText) findViewById(R.id.etCiudad);

        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,activity_mostrar.class));
            }
        });

    }


    public void crear(View view){
        //Dar de alta usuario
            AdminHelper admin = new AdminHelper(this, "administracion",null, 1);
            SQLiteDatabase db=admin.getWritableDatabase();
            //Guardar los valores que leemos de los EditText en un Registro
            //Para ello creamos el registro
        ContentValues registro = new ContentValues();
        registro.put("dni",etDni.getText().toString());
        registro.put("nombre",etNombre.getText().toString());
        registro.put("ciudad",etCiudad.getText().toString());
        registro.put("numero",etNumero.getText().toString());

        //Insertar en la BD
        db.insert("usuario", null,registro);

        //Cerrar la BD
        db.close();

        //Poner los campos a vacío para insertar el siguiente usuario
        etDni.setText("");
        etNumero.setText("");
        etNombre.setText("");
        etCiudad.setText("");

        Toast.makeText(this,"Usuario insertado en la Base de datos",Toast.LENGTH_LONG).show();
    }

    public void borrar(View view) {
        //Dar de alta usuario
        AdminHelper admin = new AdminHelper(this, "administracion",null, 1);
        SQLiteDatabase db=admin.getWritableDatabase();

        String dni= etDni.getText().toString();
        int cant = db.delete("usuario","dni='"+dni+"'",null);
        db.close();

        //Comprobación filas afectadas

        try {
            if (cant == 1) {
                //Se ha borrado un fila
                Toast.makeText(this, "Se ha borrado un usuario", Toast.LENGTH_LONG).show();

            } else {
                //No se ha borrado nada
                Toast.makeText(this, "No existe el usuario", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e){
            Toast.makeText(this, "No existe el usuario", Toast.LENGTH_LONG).show();
        }

        //Poner los campos a vacío para insertar el siguiente usuario
        etDni.setText("");
        etNumero.setText("");
        etNombre.setText("");
        etCiudad.setText("");
    }


}
