package com.llamasoda.coronasoda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.llamasoda.coronasoda.modelo.Usuariocompleto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Registrodeusuario extends AppCompatActivity {
    String FileName = "myfile";
    SharedPreferences prefs;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
TextView nombres,apellidos,telefono,direccion,correo,contrasena,recontrasena;
Button registro,mapa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registrodeusuario);

        prefs = getApplication().getSharedPreferences(FileName, Context.MODE_PRIVATE);





        String telefonoguardado = prefs.getString("telefono", "");
        String idfirebase = prefs.getString("idfirebase", "");
        String direccione=prefs.getString("direccion", "");
        String referencias=prefs.getString("referencia","");
        String latitud=prefs.getString("latitud","");
        String longitud=prefs.getString("longitud","");
        String idalmacen=prefs.getString("idalmacenactivosf","");
        Toast.makeText(getApplication(),"direccionooooooo"+longitud+latitud,Toast.LENGTH_LONG).show();
        nombres=(TextView) findViewById(R.id.nombrecompleto);
        apellidos=(TextView) findViewById(R.id.apellidos);
        telefono=(TextView) findViewById(R.id.telefono);
        contrasena=(TextView) findViewById(R.id.contra);
        direccion=(TextView) findViewById(R.id.dire);
        correo=(TextView) findViewById(R.id.correoelectronico);
        registro=(Button) findViewById(R.id.registrusuario);
        mapa=(Button) findViewById(R.id.mapa);
telefono.setText(telefonoguardado);
direccion.setText(direccione);


        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           Usuariocompleto usuario = new Usuariocompleto(nombres.getText().toString() ,apellidos.getText().toString(),contrasena.getText().toString(),"sin imagen","sin idfacebook","sin nombre ",idfirebase,"mi telefono",contrasena.getText().toString(),correo.getText().toString(),"direccionficticia",1,Integer.valueOf(idalmacen),latitud,longitud,referencias);
           new grabarusuario().execute(usuario);

            }
        });

mapa.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       obtenerdireccion();
    }
});


    }

    private void obtenerdireccion() {
        Intent pi;
        pi = new Intent(this,Mapa.class);
        startActivity(pi);
    }

    private class grabarusuario extends AsyncTask<Usuariocompleto, Void, String> {
        String resultado;
        HttpURLConnection conne;
        URL url = null;
        Usuariocompleto ped;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(Usuariocompleto... params) {
            ped=params[0];
            try {
                url = new URL("http://sodapop.space/sugest/apigrabarusuario.php");
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
            try {
                conne = (HttpURLConnection) url.openConnection();
                conne.setReadTimeout(READ_TIMEOUT);
                conne.setConnectTimeout(CONNECTION_TIMEOUT);
                conne.setRequestMethod("POST");
                conne.setDoInput(true);
                conne.setDoOutput(true);

                // Append parameters to URL



                Uri.Builder builder = new Uri.Builder()



                        .appendQueryParameter("nombreusuario",String.valueOf(ped.getNombreusuario()))
                        .appendQueryParameter("apellidos",String.valueOf(ped.getApellidos()))
                        .appendQueryParameter("claveusuario",String.valueOf(ped.getContrasena()))
                        .appendQueryParameter("imagen", String.valueOf(ped.getIdalmacen()))
                        .appendQueryParameter("idfirebase", String.valueOf(ped.getIdfirebase()))
                        .appendQueryParameter("telefono", String.valueOf(ped.getIdfacebook()))
                         .appendQueryParameter("contrasena", String.valueOf(ped.getContrasena()))
                        .appendQueryParameter("correo", String.valueOf(ped.getCorreo()))
                        .appendQueryParameter("idalmacen", String.valueOf(ped.getIdalmacen()))
                        .appendQueryParameter("direccion", String.valueOf(ped.getDireccion()));
                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conne.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conne.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return null;
            }
            try {
                int response_code = conne.getResponseCode();
                if (response_code == HttpURLConnection.HTTP_OK) {
                    InputStream input = conne.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);

                    }
                    resultado=result.toString();

                    return resultado;

                } else {

                }
            } catch (IOException e) {
                e.printStackTrace()                ;
                Log.d("mii", e.toString());
                return null;
            } finally {
                conne.disconnect();
            }
            return resultado;
        }


        @Override
        protected void onPostExecute(String resultado) {

            super.onPostExecute(resultado);

            if(resultado.equals("true")){
                Log.d("ii", "insertado");


            }else{
                String ii =resultado.toString();
                Log.d("jj", "usuario valido");


                // lanzarsistema();
            }



        }


    }
}
