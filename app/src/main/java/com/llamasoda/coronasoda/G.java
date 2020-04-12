package com.llamasoda.coronasoda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.core.ThreadInitializer;
import com.llamasoda.coronasoda.Realm.Crudetallepedido;
import com.llamasoda.coronasoda.modelo.Adicional;
import com.llamasoda.coronasoda.modelo.Crema;

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
import java.util.List;
import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Realm;
import com.llamasoda.coronasoda.Realm.*;
import com.llamasoda.coronasoda.modelo.Detallepedido;

import io.realm.RealmConfiguration;
import static com.llamasoda.coronasoda.Login.CONNECTION_TIMEOUT;
import static com.llamasoda.coronasoda.Login.READ_TIMEOUT;

public class G extends AppCompatActivity {
    int numerodeadiciones;
    String idproductoseleccionado;
    TextView totalapagar;
int idsupremodedetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        Realm.init(getApplication());
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("pedido.realm")
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(realmConfig);
        setContentView(R.layout.activity_g);
        totalapagar=(TextView)findViewById(R.id.totalapagar);
        //datos desde atras
        Intent myIntent = getIntent();
        String nombredeproductoseleccionado = myIntent.getStringExtra("nombredeproductoseleccionado"); // will return "FirstKeyValue"
        String preciodeproductoseleccionado= myIntent.getStringExtra("preciodeproductoseleccionado"); // will return "SecondKeyValue"
 idproductoseleccionado= myIntent.getStringExtra("idproductoseleccionado"); // will return "SecondKeyValue"


        realmgrbarenbasedatosdetallepedido(Integer.parseInt(idproductoseleccionado),1,nombredeproductoseleccionado,Double.parseDouble(preciodeproductoseleccionado),1,0.0);
      idsupremodedetalle=Crudetallepedido.calculateIndex();
        Animation a = AnimationUtils.loadAnimation(getApplication(), R.anim.dechicoagrande);
        a.reset();


        TextView cabecera=(TextView) findViewById(R.id.cabeceralayoutadicional);
        TextView cantidadapedir=(TextView) findViewById(R.id.cantidadapedir);

        Button mas=(Button) findViewById(R.id.botonmas) ;
        Button menos=(Button) findViewById(R.id.botonmenos) ;
        cabecera.setText(nombredeproductoseleccionado);
        cantidadapedir.setText("1");
        totalapagar.setText(String.valueOf(preciodeproductoseleccionado));

        totalapagar.clearAnimation();
        totalapagar.startAnimation(a);

        mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String cantidad=cantidadapedir.getText().toString();
                int c= Integer.parseInt(cantidad);
                if(c>=1){
                 c=c+1;
                 cantidadapedir.setText( String.valueOf(c));
                 String va=totalapagar.getText().toString();
                    Double totalitoinicial=Double.parseDouble(va);
                    totalitoinicial=totalitoinicial+(totalitoinicial/(c-1));
                    totalapagar.setText(String.valueOf(totalitoinicial));
                    totalapagar.clearAnimation();
                    totalapagar.startAnimation(a);

                }
            }
        });
        menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cantidad=cantidadapedir.getText().toString();
                int c= Integer.parseInt(cantidad);
                if(c>1) {
                    c = c - 1;
                    cantidadapedir.setText(String.valueOf(c));
                    String va = totalapagar.getText().toString();
                    Double totalitoinicial = Double.parseDouble(va);
                    totalitoinicial=totalitoinicial-(totalitoinicial/(c+1));
                    totalapagar.setText(String.valueOf(totalitoinicial));
                    totalapagar.clearAnimation();
                    totalapagar.startAnimation(a);

                }            }
        });

        new traeradicional().execute(idproductoseleccionado);
        new traercremas().execute(idproductoseleccionado);
    }
    private class traeradicional extends AsyncTask<String, String, String> {
        HttpURLConnection conne;
        URL url = null;
        ArrayList<Adicional> listadeadicionales = new ArrayList<Adicional>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL("https://sodapop.pe/sugest/apitraeradicional.php");
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            try {
                conne = (HttpURLConnection) url.openConnection();
                conne.setReadTimeout(READ_TIMEOUT);
                conne.setConnectTimeout(CONNECTION_TIMEOUT);
                conne.setRequestMethod("POST");
                conne.setDoInput(true);
                conne.setDoOutput(true);
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("idproducto", params[0]);
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
                return e1.toString();
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
                    return (
                            result.toString()
                    );
                } else {
                    return ("Connection error");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conne.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Animation a = AnimationUtils.loadAnimation(getApplication(), R.anim.dechicoagrande);
            a.reset();

            ArrayList<Adicional> peopleadicional = new ArrayList<>();
            String[] stradicional = {"No Suggestions"};
            ArrayList<String> datalistadicional = new ArrayList<String>();


            Adicional mesoadiconal;
            peopleadicional.clear();
            RecyclerView.Adapter adapteradicional;

            if (result.equals("no rows")) {
            } else {
                try {
                    JSONArray jArray = new JSONArray(result);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.optJSONObject(i);
                        mesoadiconal = new Adicional(json_data.getInt("idadicional"), json_data.getString("nombreadicional"), json_data.getDouble("precioadicional"), json_data.getString("estadoadicional"));
                        peopleadicional.add(mesoadiconal);
                    }
                    LinearLayout my_layout = (LinearLayout)findViewById(R.id.my_layout);

                    stradicional = datalistadicional.toArray(new String[datalistadicional.size()]);



                    TextView texto = new TextView(getApplication());
                    texto.setText("        AGREGA ALGUN ADICIONAL        ");
                    texto.setBackgroundDrawable(getApplication().getResources().getDrawable(R.drawable.blue_leftcorner_bkg));
                    texto.setGravity(Gravity.CENTER);

                    //  texto.setLayoutParams(param);
                    texto.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                    texto.setTypeface(null, Typeface.BOLD);
                    texto.setShadowLayer(2, 1, 1, R.color.accent);
                    texto.setTextColor(getApplication().getResources().getColor(R.color.colortres));

                    TableRow.LayoutParams textoenlayout = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                    my_layout.addView(texto, textoenlayout);

                    for( numerodeadiciones= 0; numerodeadiciones < peopleadicional.size(); numerodeadiciones++) {
                        CheckBox cb = new CheckBox(getApplication());
                        cb.setText("   "+peopleadicional.get(numerodeadiciones).getNombreadicional()+ "               S/. "+String.valueOf(peopleadicional.get(numerodeadiciones).getPrecioadicional()));
            Double ffff=peopleadicional.get(numerodeadiciones).getPrecioadicional();
                        String q=peopleadicional.get(numerodeadiciones).getNombreadicional();
                        Double l=peopleadicional.get(numerodeadiciones).getPrecioadicional();



                        cb.setId(numerodeadiciones);
                        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                            @Override
                            public void onCheckedChanged(CompoundButton buttonView,
                                                         final boolean isChecked) {
                                CharSequence options[];
                                if (isChecked) {
                                    //String preciodeadicional=String.valueOf(peopleadicional.get(numerodeadiciones).getPrecioadicional());

                                    realmgrabaradicional( q,l,Integer.parseInt(idproductoseleccionado));



                                    TextView totalapaga=(TextView)findViewById(R.id.totalapagar);
                                    TextView cantidadapedir=(TextView)findViewById(R.id.cantidadapedir);
                                    String ct=cantidadapedir.getText().toString();


                                    String vad=totalapaga.getText().toString();
                                    Double totalitoinicial=Double.parseDouble(vad);
                                    totalitoinicial=totalitoinicial+(ffff*Double. parseDouble(ct));
                                    totalapagar.setText(String.valueOf(totalitoinicial));
                                    totalapagar.clearAnimation();
                                    totalapagar.startAnimation(a);

                                } else {
                                    TextView cantidadapedir=(TextView)findViewById(R.id.cantidadapedir);
                                    String ct=cantidadapedir.getText().toString();

                                    TextView totalapaga=(TextView)findViewById(R.id.totalapagar);
                                    String vad=totalapaga.getText().toString();
                                    Double totalitoinicial=Double.parseDouble(vad);
                                    totalitoinicial=totalitoinicial-(ffff*Double. parseDouble(ct));;
                                    totalapagar.setText(String.valueOf(totalitoinicial));
                                    totalapagar.clearAnimation();
                                    totalapagar.startAnimation(a);



                                    eliminaradicional(idsupremodedetalle,Integer.parseInt(idproductoseleccionado),q);
                                }
                            }});



                        my_layout.addView(cb);
                    }








                } catch (JSONException e) {
                    Log.d("erroro",e.toString());
                }
            }
        }

    }
    private class traercremas extends AsyncTask<String, String, String> {
        HttpURLConnection conne;
        URL url = null;
        ArrayList<Adicional> listadeadicionales = new ArrayList<Adicional>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL("https://sodapop.pe/sugest/apicremas.php");
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
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

                        .appendQueryParameter("idproducto", params[0]);

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
                return e1.toString();
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
                    return (

                            result.toString()


                    );

                } else {
                    return ("Connection error");
                }
            } catch (IOException e) {
                e.printStackTrace();

                return e.toString();
            } finally {
                conne.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            ArrayList<Crema> peoplecrema = new ArrayList<>();
            String[] strcrema = {"No Suggestions"};
            ArrayList<String> datalistcrema = new ArrayList<String>();
            Crema mesocrema;
            peoplecrema.clear();
            if (result.equals("no rows")) {
            } else {
                try {
                    JSONArray jArray = new JSONArray(result);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.optJSONObject(i);
                        mesocrema = new Crema(json_data.getInt("idcrema"), json_data.getString("nombrecrema"),  json_data.getString("estadocrema"));
                        peoplecrema.add(mesocrema);
                    }
                    LinearLayout my_layout = (LinearLayout)findViewById(R.id.my_layout);
                    strcrema = datalistcrema.toArray(new String[datalistcrema.size()]);
                    TextView texto = new TextView(getApplication());
                    texto.setText("        ESCOGE TUS CREMAS        ");
                    texto.setBackgroundDrawable(getApplication().getResources().getDrawable(R.drawable.blue_leftcorner_bkg));
                    texto.setGravity(Gravity.CENTER);

                  //  texto.setLayoutParams(param);
                    texto.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                    texto.setTypeface(null, Typeface.BOLD);
                    texto.setShadowLayer(2, 1, 1, R.color.accent);
                    texto.setTextColor(getApplication().getResources().getColor(R.color.colortres));

                    TableRow.LayoutParams textoenlayout = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                    my_layout.addView(texto, textoenlayout);

                    int numerodecrema;
                    for(numerodecrema= 0; numerodecrema < peoplecrema.size(); numerodecrema++) {
                        CheckBox cbc = new CheckBox(getApplication());
                        cbc.setText("   "+peoplecrema.get(numerodecrema).getNombrecrema());
                        cbc.setId(numerodecrema+1);

                        cbc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView,
                                                         final boolean isChecked) {

                                CharSequence options[];

                                if (isChecked) {

                                    Toast.makeText(getApplicationContext(),"esta activo"+String.valueOf(cbc.getId()),Toast.LENGTH_LONG).show();

                                } else {
                                    Toast.makeText(getApplicationContext(),"esta desactivadazooooooo"+String.valueOf(cbc.getId())+"entotal hay"+String.valueOf(numerodeadiciones),Toast.LENGTH_LONG).show();
                                }
                            }});



                        my_layout.addView(cbc);
                    }


                    Button btn = new Button(getApplication());
                    btn.setText("Listo");

                    TableRow.LayoutParams buttonlayout = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
                    my_layout.addView(btn, buttonlayout);







                } catch (JSONException e) {
                    Log.d("erroro",e.toString());
                }
            }
        }
    }










    public  static void realmgrbarenbasedatosdetallepedido(final int idproducto,int cantidad,String nombre,Double precio,int idpedido,Double subtotal) {
        Realm pedido = Realm.getDefaultInstance();
        pedido.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm pedido) {
                int index = Crudetallepedido.calculateIndex();
                Detallepedidorealm realmDetallepedidorealm = pedido.createObject(Detallepedidorealm.class, index);
                realmDetallepedidorealm.setIdproductorealm(idproducto);
                realmDetallepedidorealm.setCantidadrealm(cantidad);
                realmDetallepedidorealm.setNombreproductorealm(nombre);
                realmDetallepedidorealm.setPrecventarealm(precio);
                //realmDetallepedidorealm.setIdpedido(index);
                realmDetallepedidorealm.setSubtotal(subtotal);
            }

        });
        Log.d("TAG", "se creara por primera vez el detalle");

    }

    public  static void realmgrabaradicional(final String nombreadicional,Double precioadicional,int idproducto) {
        Realm pedido = Realm.getDefaultInstance();
        pedido.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm pedido) {
                int index = CrudadicionalRealm.calculateIndex();
                int iddedetalle=Crudetallepedido.calculateIndex();
                AdicionalRealm AdicionalRealm = pedido.createObject(AdicionalRealm.class, index);

                AdicionalRealm.setId(iddedetalle-1);
                AdicionalRealm.setIdproducto(idproducto);
                AdicionalRealm.setNombreadicional(nombreadicional);
                AdicionalRealm.setPrecioadicional(precioadicional);
                AdicionalRealm.setEstadoadicional("1");

            }
        });
        Log.d("TAG", "se creara por primera vez el detalle");

    }
    public  static void realgrabarcrema(final String nombrecrema,int idproducto) {
        Realm pedido = Realm.getDefaultInstance();
        pedido.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm pedido) {
                int index = CrudcremaRealm.calculateIndex();
                int iddedetalle=Crudetallepedido.calculateIndex();

                CremaRealm CremaRealm = pedido.createObject(CremaRealm.class, index);
                CremaRealm.setId(iddedetalle-1);
                CremaRealm.setEstadocrema("1");
                CremaRealm.setIdproducto(idproducto);
                CremaRealm.setNombrecrema(nombrecrema);
            }
        });
        Log.d("TAG", "se creara por primera vez el detalle");

    }
    public final static List<AdicionalRealm> eliminaradicional(int ido,int idproducto,String nombreadicional) {
        Realm pedido = Realm.getDefaultInstance();
int y=ido-1;
        RealmResults<AdicionalRealm> results =
                pedido.where(AdicionalRealm.class).
                        equalTo("id", y)
                        .equalTo("idproducto", idproducto)
                        .equalTo("nombreadicional",nombreadicional)
                        .findAll();
        results.toString().trim();
        pedido.beginTransaction();
        results.deleteFirstFromRealm();     // App crash
        pedido.commitTransaction();


        return results;
    }

    public final static List<CremaRealm> eliminarcrema(String ido,int idproducto) {
        Realm realm = Realm.getDefaultInstance();
        //int iddedetalle=Crudetallepedido.calculateIndex();
        RealmResults<CremaRealm> AdicionalRealmo = realm.where(CremaRealm.class).equalTo("id", ido).equalTo("idproducto", idproducto).findAll();
        AdicionalRealmo.deleteFirstFromRealm();

        realm.commitTransaction();

        return AdicionalRealmo;
    }
    public  static void elimina(int ido,int idproducto) {
        Realm pedidoo = Realm.getDefaultInstance();
        pedidoo.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm pedidoo) {

                Objects.requireNonNull(pedidoo.where(AdicionalRealm.class).
                        equalTo("id", ido)
                        .equalTo("idproducto", idproducto)
                        .findFirst());






            }
        });
        Log.d("TAG", "se creara por primera vez el detalle");

    }

}




