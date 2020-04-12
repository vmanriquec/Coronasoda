package com.llamasoda.coronasoda;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.llamasoda.coronasoda.modelo.Almacen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.ArrayList;

public class Login extends AppCompatActivity {
String nombreusuario1;
String claveusuario;

    private String[] strArrData = {"No Suggestions"};
    String FileName ="myfile";
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

            final TextView nombreuser=(TextView) findViewById(R.id.phpnombreusuario);
            final TextView claveusuario=(TextView) findViewById(R.id.phpclaveusuario);
            final Spinner spinerio=(Spinner) findViewById(R.id.spinnerio);
        Button va=(Button) findViewById(R.id.phpbtnloginphp) ;
        new cargaralmacen().execute();
        va.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if( nombreuser.getText().toString().length() == 0 || claveusuario.getText().toString().length() == 0 ){
                    nombreuser.setError( "Debes digitar un nombre y clave de usuario" );
                }
                else{
                    if( nombreuser.getText().toString().length() == 0 ){
                        nombreuser.setError( "Debes digitar un nombre usuario" );
                    }   else{
                        if( claveusuario.getText().toString().length() == 0 ){
                            claveusuario.setError( "Debes digitar su clave" );
                        }else{
                            String al =spinerio.getItemAtPosition(spinerio.getSelectedItemPosition()).toString();
                            String mesei=al;
                            String mesi = mesei.substring(0, 2);
                            String mei=mesi.trim();
                            new Loginsinfacebook().execute(nombreuser.getText().toString(),claveusuario.getText().toString(),mei);
                        }

                    }
                }
            }
        });
    }
    private class Loginsinfacebook extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(Login.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tCargando...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }
        @Override
        protected String doInBackground(String... params) {
            try {
              url = new URL("https://www.sodapop.pe/sugest/apilogin.php");
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("username", params[0])
                        .appendQueryParameter("password", params[1])
                        .appendQueryParameter("idalmacen", params[2]);
                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return(result.toString());

                }else{

                    return("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();

            if(result.equalsIgnoreCase("true"))
            {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */



                final TextView nombreuser=(TextView) findViewById(R.id.phpnombreusuario);
                final TextView claveusuario=(TextView) findViewById(R.id.phpclaveusuario);
                final Spinner spinerio=(Spinner) findViewById(R.id.spinnerio);

                if( nombreuser.getText().toString().length() == 0 || claveusuario.getText().toString().length() == 0 ){
                    nombreuser.setError( "Debes digitar un nombre y clave de usuario" );

                }
                else {
                    if (nombreuser.getText().toString().length() == 0) {
                        nombreuser.setError("Debes digitar un nombre usuario");

                    } else {
                        if (claveusuario.getText().toString().length() == 0) {
                            claveusuario.setError("Debes digitar su clave");

                        } else {

                            String al = spinerio.getItemAtPosition(spinerio.getSelectedItemPosition()).toString();
                            String mesei = al;
                            String mesi = mesei.substring(0, 2);
                            String mei = mesi.trim();


                            guardarsharesinfacebook(nombreuser.getText().toString(), claveusuario.getText().toString());

                            ir();

                        }

                    }

                }

            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                Toast.makeText(Login.this, "no estas autorizado a hacer operaciones en este almacen", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(Login.this, "OOPs! hay problemas de conexion...", Toast.LENGTH_LONG).show();

            }
        }

    }
    private class cargaralmacen extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog(Login.this);
        HttpURLConnection conn;
        URL url = null;
        ArrayList<Almacen> listaalmacen = new ArrayList<Almacen>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tCargando Almacenes");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
          Spinner  spin=(Spinner) findViewById(R.id.spinnerio);
            try {
                url = new URL("https://www.sodapop.pe/sugest/fetch-all-fish.php");
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");
                conn.setDoOutput(true);
                conn.connect();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }
            try {
                int response_code = conn.getResponseCode();

                if (response_code == HttpURLConnection.HTTP_OK) {

                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    return (result.toString());
                } else {
                    return("Connection error");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }
        }
        @Override
        protected void onPostExecute(String result) {
            ImageView im=(ImageView) findViewById(R.id.ima);
           Spinner spin=(Spinner) findViewById(R.id.spinnerio);
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.dechicoagrande);
            im.startAnimation(animation);
            ArrayList<String> dataList = new ArrayList<String>();
            Almacen mes;
            if(result.equals("no rows")) {
                Toast.makeText(Login.this,"no existen datos a mostrar",Toast.LENGTH_LONG).show();

            }else{

                try {

                    JSONArray jArray = new JSONArray(result);

                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.getJSONObject(i);
                        dataList.add(json_data.getString("nombrealm"));
                        mes = new Almacen(json_data.getInt("idalmacen"), json_data.getString("nombrealm"), json_data.getString("telfonoalm"), json_data.getString("correoalm"));
                        listaalmacen.add(mes);
                    }
                    strArrData = dataList.toArray(new String[dataList.size()]);

                    ArrayAdapter<Almacen> adaptadorl= new ArrayAdapter<Almacen>(Login.this, android.R.layout.simple_spinner_item,listaalmacen );
                    spin.setAdapter(adaptadorl);


                } catch (JSONException e) {
                }

            }
            pdLoading.dismiss();


        }

    }
    private  void guardarsharesinfacebook(String nombreusuario,String claveusuario){
        SharedPreferences sharedPreferences =getSharedPreferences(FileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Spinner s=(Spinner)findViewById(R.id.spinnerio);
        String al =s.getItemAtPosition(s.getSelectedItemPosition()).toString();
        String mesei=al;
        int g= mesei.length();
        String mesi = mesei.substring(0,2);

        String  idalmacenactivosf=mesi.trim();

        String mesio = mesei.substring(3,g);
        String almacenactivosf=mesio.trim();
        editor.putString("facebook","no");
        editor.putString("nombreusuariof",nombreusuario);
        editor.putString("claveusuariof",claveusuario);
        editor.putString("almacenactivosf",almacenactivosf);
        editor.putString("idalmacenactivosf",idalmacenactivosf);
        editor.putString("editandopedidof","no");

        editor.commit();

    }
    private void ir(){
        Intent i= new Intent(this,intentIniciodeldia.class);
        startActivity(i);
    }
}
