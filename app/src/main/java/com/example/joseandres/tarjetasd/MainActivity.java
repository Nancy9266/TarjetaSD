package com.example.joseandres.tarjetasd;

import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends ActionBarActivity {

    private EditText edt1,edt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt1=(EditText)findViewById(R.id.edt1);
        edt2=(EditText)findViewById(R.id.edt2);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //El método para grabar los datos en un archivo de texto localizado en una tarjeta SD
    // comienza a través del método getExternalStorageDirectory(),el mismo retorna un objeto
    // de la clase File.
    public void grabar(View v) {
        String nomarchivo = edt1.getText().toString();
        String contenido = edt2.getText().toString();
        try {
            File tarjeta = Environment.getExternalStorageDirectory();
            Toast.makeText(this,tarjeta.getAbsolutePath(),Toast.LENGTH_LONG).show();
            //Creamos un nuevo objeto de la clase File indicando el camino de la unidad SD
            // y el nombre del archivo a crear.
            File file = new File(tarjeta.getAbsolutePath(), nomarchivo);
            //Similar al acceso de un archivo interno creamos un objeto de la clase
            // OutputStreamWriter.
            OutputStreamWriter osw = new OutputStreamWriter(
                    new FileOutputStream(file));
            //Grabamos el contenido del EditText.
            osw.write(contenido);
            //Cerramos el archivo.
            osw.flush();
            osw.close();
            Toast.makeText(this, "Los datos fueron grabados correctamente",
                    Toast.LENGTH_SHORT).show();
            edt1.setText("");
            edt2.setText("");
        } catch (IOException ioe) {
            Toast.makeText(this, "No se pudo grabar",
                    Toast.LENGTH_SHORT).show();
        }
    }
    //Para la lectura del archivo nuevamente obtenemos la referencia de la tarjeta SD para
    // obtener el path de la unidad de almacenamiento, el resto del algoritmo es similar al
    // visto con un archivo interno.
    public void recuperar(View v) {
        String nomarchivo = edt1.getText().toString();
        File tarjeta = Environment.getExternalStorageDirectory();
        File file = new File(tarjeta.getAbsolutePath(), nomarchivo);
        try {
            FileInputStream fIn = new FileInputStream(file);
            InputStreamReader archivo = new InputStreamReader(fIn);
            BufferedReader br = new BufferedReader(archivo);
            String linea = br.readLine();
            String todo = "";
            while (linea != null) {
                todo = todo + linea + " ";
                linea = br.readLine();
            }
            br.close();
            archivo.close();
            edt2.setText(todo);

        } catch (IOException e) {
            Toast.makeText(this, "No se pudo leer",
                    Toast.LENGTH_SHORT).show();
        }
    }

}

