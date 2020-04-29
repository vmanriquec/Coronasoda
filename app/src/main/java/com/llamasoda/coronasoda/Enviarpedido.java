package com.llamasoda.coronasoda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.llamasoda.coronasoda.Realm.AdicionalRealm;
import com.llamasoda.coronasoda.Realm.AdicionalRealmFirebase;
import com.llamasoda.coronasoda.Realm.CremaRealm;
import com.llamasoda.coronasoda.Realm.CremaRealmFirebase;
import com.llamasoda.coronasoda.Realm.CrudadicionalRealm;
import com.llamasoda.coronasoda.Realm.CrudcremaRealm;
import com.llamasoda.coronasoda.Realm.Crudetallepedido;
import com.llamasoda.coronasoda.Realm.Crudpedido;
import com.llamasoda.coronasoda.Realm.DetallepedidoRealmFirebase;
import com.llamasoda.coronasoda.Realm.Detallepedidorealm;
import com.llamasoda.coronasoda.Realm.PedidoRealm;
import com.llamasoda.coronasoda.Realm.PedidoRealmFirebase;
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
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class Enviarpedido extends AppCompatActivity {
    String FileName = "myfile";
    SharedPreferences prefs;
    String almacen, pagocliente, vuelto;
    private static final String FPEDIDOS = "PEDIDOS";
    private static final String FDETALLEPEDIDO = "FDETALLEPEDIDO";
    private static final String FCREMAS = "FCREMAS";
    private static final String FADICIONAL = "FADICIONAL";
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
Button limpiar;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviarpedido);
        prefs = getApplication().getSharedPreferences(FileName, Context.MODE_PRIVATE);
        almacen = prefs.getString("idalmacenactivosf", "");
        limpiar=(Button) findViewById(R.id.limpiartodo) ;
limpiar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        borrartodo();
    }
});

        escribiren(0);
    }


    public final static List<CremaRealm> todocrema(int ido) {
        Realm pedido = Realm.getDefaultInstance();

        RealmResults<CremaRealm> results =
                pedido.where(CremaRealm.class).
                        equalTo("id", ido)
                        .findAll();
        results.toString().trim();

        pedido.commitTransaction();
        return results;
    }

    public final static List<AdicionalRealm> todoadicional(int ido) {
        Realm pedido = Realm.getDefaultInstance();

        RealmResults<AdicionalRealm> results =
                pedido.where(AdicionalRealm.class).
                        equalTo("id", ido)
                        .findAll();
        results.toString().trim();

        pedido.commitTransaction();
        return results;
    }

    public final static List<Detallepedidorealm> tododetalle(int ido) {
        Realm pedido = Realm.getDefaultInstance();

        RealmResults<Detallepedidorealm> results =
                pedido.where(Detallepedidorealm.class).
                        equalTo("id", ido)
                        .findAll();
        results.toString().trim();

        pedido.commitTransaction();
        return results;
    }


    private void escribiren(int id) {
        prefs = getApplication().getSharedPreferences(FileName, Context.MODE_PRIVATE);
        String telefonoguardado = prefs.getString("telefono", "");
        String idfirebase = prefs.getString("idfirebase", "mi fire");
        String direccione = prefs.getString("direccion", "");
        String referencias = prefs.getString("referencia", "");
        String latitudp = prefs.getString("latitud", "");
        String longitudp = prefs.getString("longitud", "");
        String nombredescuento = prefs.getString("nombredescuento", "");
        String montodescuento = prefs.getString("montodescuento", "");
        String nombrecosto = prefs.getString("nombrecosto", "");
        String montocosto = prefs.getString("montocosto", "");
        String totalpedido = prefs.getString("totalpedido", "");
        String nombreusuariof = prefs.getString("nombreusuariof", "");


        pagocliente = prefs.getString("cuantopagacliente", "");
        vuelto = prefs.getString("vuelto", "");


        Realm pedido = Realm.getDefaultInstance();

        ArrayList<PedidoRealmFirebase> todoslospedidos = new ArrayList<>();
        RealmResults<PedidoRealm> resultsp =
                pedido.where(PedidoRealm.class).
                        equalTo("idpedido", id)
                        .findAll();

        int wp = resultsp.size();
        for (int i = 0; i < wp; i++) {
            int idpedidop = resultsp.get(i).getIdpedido();
            int idalmacenp = resultsp.get(i).getIdalmacen();
            int idclientep = resultsp.get(i).getIdcliente();
            String descripcionpedidop = resultsp.get(i).getDescripcionpedido();
            String estadopedidop = "generado";
            Double totalp = resultsp.get(i).getTotal();
            int idmesap = resultsp.get(i).getIdmesa();
            String fechapedidop = resultsp.get(i).getFechapedido();
            String idfacebookp = resultsp.get(i).getIdfacebook();
            String descripcionp = resultsp.get(i).getDescripcionpedido();
            int idusuariop = resultsp.get(i).getIdusuario();
            PedidoRealmFirebase ped = new PedidoRealmFirebase();
            todoslospedidos.add(ped);
            PedidoRealmFirebase prf = new PedidoRealmFirebase(idpedidop,
                    idclientep,
                    idmesap,
                    Double.parseDouble(totalpedido),
                    estadopedidop,
                    fechapedidop,
                    idusuariop,
                    idalmacenp,
                    idfacebookp
                    , descripcionpedidop,
                    "1",
                    direccione,
                    idfirebase,
                    nombredescuento,
                    montodescuento,
                    nombrecosto,
                    montocosto,
                    longitudp,
                    latitudp,
                    pagocliente,
                    vuelto,
                    telefonoguardado,
                    referencias,
                    nombreusuariof);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference(FPEDIDOS);
            reference.child(idfirebase).setValue(prf);

            new grabarpedido().execute(prf);


        }


        String f;

        ArrayList<DetallepedidoRealmFirebase> todoslosdetalles = new ArrayList<>();
        //sacar el todoslosdetalles
        RealmResults<Detallepedidorealm> results =
                pedido.where(Detallepedidorealm.class)
                        .findAll();
        int w = results.size();
        DetallepedidoRealmFirebase det ;
        for (int i = 0; i < w; i++) {
            int cantidad = results.get(i).getCantidadrealm();
            int idd = results.get(i).getId();
            int idpedido = results.get(i).getIdpedido();
            String nombreproducto = results.get(i).getNombreproductorealm();
            String subtotal = results.get(i).getSubtotal();
            Double precvente = results.get(i).getPrecventarealm();
            int idproductorealm = results.get(i).getIdproductorealm();
            String comentariococina = results.get(i).getComentarioacocina();
 det = new DetallepedidoRealmFirebase(idd, 1, subtotal, idfirebase, cantidad, precvente, nombreproducto, "", Integer.parseInt(almacen), idproductorealm, comentariococina);


            todoslosdetalles.add(det);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference(FPEDIDOS);
            String pppp = String.valueOf(det.getIdproductorealm() + i);
            new grabardetalle().execute(det);
////aquigrabacreasporcadadetalle
            RealmResults<CremaRealm> resulcremaa =
                    pedido.where(CremaRealm.class)
                            .equalTo("id", det.getId())
                            .findAll();

            int lsrgaa = resulcremaa.size();

            for (int ic = 0; ic < lsrgaa; ic++) {
                RealmResults<CremaRealm> resultsoa =
                        pedido.where(CremaRealm.class)
                                .equalTo("id", det.getId())
                                .findAll();
                CremaRealmFirebase dett = new CremaRealmFirebase(ic, resultsoa.get(ic).getNombrecrema().toString(), "1", 1, idproductorealm);
                new grabarcrema().execute(dett);

            }
/////////////////////
            RealmResults<AdicionalRealm> resultadicionala =
                    pedido.where(AdicionalRealm.class)
                            .equalTo("id", det.getId())
                            .findAll();
            int cuantosa = resultadicionala.size();
            for (int ia = 0; ia < cuantosa; ia++) {
                RealmResults<AdicionalRealm> unoporunoa =
                        pedido.where(AdicionalRealm.class)
                                .equalTo("id", det.getId())
                                .findAll();
                AdicionalRealmFirebase dettaa = new AdicionalRealmFirebase(
                        unoporunoa.get(ia).getIdadicional(),unoporunoa.get(ia).getNombreadicional(),unoporunoa.get(ia).getPrecioadicional(),
                        unoporunoa.get(ia).getEstadoadicional(),unoporunoa.get(ia).getIdproducto(),unoporunoa.get(ia).getId());
                new grabaradicional().execute(dettaa);
            }









            reference.child(idfirebase).child("PRODUCTOS").child(pppp).setValue(det);

//lleno cremas

            RealmResults<CremaRealm> resulcrema =
                    pedido.where(CremaRealm.class)
                            .equalTo("id", idd)
                            .findAll();

            int lsrga = resulcrema.size();

            for (int ic = 0; ic < lsrga; ic++) {
                RealmResults<CremaRealm> resultso =
                        pedido.where(CremaRealm.class)
                                .equalTo("id", idd)
                                .findAll();
                    CremaRealmFirebase dett = new CremaRealmFirebase(ic, resultso.get(ic).getNombrecrema().toString(), "1", 1, resultso.get(ic).getIdproducto());
                    FirebaseDatabase databaseec = FirebaseDatabase.getInstance();
                    DatabaseReference referenceec = databaseec.getReference(FPEDIDOS);
                    String pppo = String.valueOf(dett.getIdcrema()+"c");

                reference.child(idfirebase).child("PRODUCTOS").child(pppp).child("CREMA").child(pppo).setValue(dett);
                    }

        //lleno adicionales
            RealmResults<AdicionalRealm> resultadicional =
                    pedido.where(AdicionalRealm.class)
                            .equalTo("id", idd)
                            .findAll();
            int cuantos = resultadicional.size();
            for (int ia = 0; ia < cuantos; ia++) {
                RealmResults<AdicionalRealm> unoporuno =
                        pedido.where(AdicionalRealm.class)
                                .equalTo("id", idd)
                                .findAll();
                AdicionalRealmFirebase detta = new AdicionalRealmFirebase(
                        unoporuno.get(ia).getIdadicional(),unoporuno.get(ia).getNombreadicional(),unoporuno.get(ia).getPrecioadicional(),
                        unoporuno.get(ia).getEstadoadicional(),unoporuno.get(ia).getIdproducto(),unoporuno.get(ia).getId());
                FirebaseDatabase databaseeca = FirebaseDatabase.getInstance();
                DatabaseReference referenceeca = databaseeca.getReference(FPEDIDOS);
                String pppoa = String.valueOf(detta.getIdadicional()+"ia");
                String e=detta.getNombreadicional().toString().trim();
                reference.child(idfirebase).child("PRODUCTOS").child(pppp).child("ADICIONAL").child(pppoa).setValue(detta);

            }


        }




    }

    public void guardarestadodeunpedido() {
        // SharedPreferences sharedPreferences = getSharedPreferences(FileName, Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.putString("estadopedido", estadopedido)w;

        //editor.commit();


    }

    private void borrartodo() {
        Eliminartotaladicionals();
        Eliminartotalcremas();
        Eliminartotalpedidos();
        Eliminartotaldetalles();
    }
    public final static List<CremaRealm> Eliminartotalcremas() {
        Realm pedido = Realm.getDefaultInstance();

        int ido = CrudcremaRealm.calculateIndex()-1;
        RealmResults<CremaRealm> results =pedido.where(CremaRealm.class).
                equalTo("id", ido)
                .findAll();

        pedido.beginTransaction();
        results.deleteAllFromRealm();     // App crash
        pedido.commitTransaction();
        return results;
    }
    public final static List<AdicionalRealm> Eliminartotaladicionals() {
        Realm pedido = Realm.getDefaultInstance();

        int ido = CrudadicionalRealm.calculateIndex()-1;
        RealmResults<AdicionalRealm> results =pedido.where(AdicionalRealm.class).
                equalTo("id", ido)
                .findAll();

        pedido.beginTransaction();
        results.deleteAllFromRealm();     // App crash
        pedido.commitTransaction();
        return results;

    }

    public final static List<Detallepedidorealm> Eliminartotaldetalles() {
        Realm pedido = Realm.getDefaultInstance();

        int ido = Crudetallepedido.calculateIndex()-1;
        RealmResults<Detallepedidorealm> results =pedido.where(Detallepedidorealm.class).
                equalTo("id", ido)
                .findAll();

        pedido.beginTransaction();
        results.deleteAllFromRealm();     // App crash
        pedido.commitTransaction();
        return results;

    }
    public final static List<PedidoRealm> Eliminartotalpedidos() {
        Realm pedido = Realm.getDefaultInstance();

int ido =Crudpedido.calculateIndex()-1;
        RealmResults<PedidoRealm> results =pedido.where(PedidoRealm.class).
                        equalTo("id", ido)
                        .findAll();

        pedido.beginTransaction();
        results.deleteAllFromRealm();     // App crash
        pedido.commitTransaction();
        return results;

    }
    private class grabarpedido extends AsyncTask<PedidoRealmFirebase, Void, String> {
        String resultado;
        HttpURLConnection conne;
        URL url = null;
        PedidoRealmFirebase ped;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(PedidoRealmFirebase... params) {
            ped=params[0];
            try {
                url = new URL("https://sodapop.pe/sugest/grabarpedidofirebase.php");
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


                Log.d("valor",String.valueOf(ped.getLongitud()));
                Uri.Builder builder = new Uri.Builder()


                        .appendQueryParameter("idcliente",String.valueOf(ped.getIdusuario()))
                        .appendQueryParameter("idmesa",String.valueOf(ped.getIdmesa()))
                        .appendQueryParameter("totalpedido",String.valueOf(ped.getTotalpedido()))
                        .appendQueryParameter("esatdopedido", String.valueOf(ped.getEstadopedido()))
                        .appendQueryParameter("fechapedido", String.valueOf(ped.getFechapedido()))
                        .appendQueryParameter("idusuario", String.valueOf(ped.getIdusuario()))
                        .appendQueryParameter("idalmacen", String.valueOf(ped.getIdalmacen()))
                        .appendQueryParameter("idfacebook", String.valueOf(ped.getIdfacebook()))


                        .appendQueryParameter("observaciones", String.valueOf(ped.getDescripcionpedido()))
                        .appendQueryParameter("llevar", String.valueOf(ped.getLlevar()))
                        .appendQueryParameter("direccionllevar", String.valueOf(ped.getDireccionallevar()))
                        .appendQueryParameter("idfirebase", String.valueOf(ped.getIdfirebase()))
                        .appendQueryParameter("latitud", String.valueOf(ped.getLatitud()))
                    .appendQueryParameter("longitud", String.valueOf(ped.getLongitud()))
                    .appendQueryParameter("costodelivery", String.valueOf(ped.getMontocosto()))
                    .appendQueryParameter("telefono", String.valueOf(ped.getTelefono()))
           .appendQueryParameter("nombredescuento", String.valueOf(ped.getNombredescuento()))
                    .appendQueryParameter("montodescuento", String.valueOf(ped.getMontodescuento()))
                    .appendQueryParameter("nombrecosto", String.valueOf(ped.getNombrecosto()))
                    .appendQueryParameter("montocosto", String.valueOf(ped.getMontocosto()))
                    .appendQueryParameter("pagocliente", String.valueOf(ped.getCuantopagaecliente()))
                    .appendQueryParameter("vuelto", String.valueOf(ped.getVuelto()))
                    .appendQueryParameter("telefonoguardado", String.valueOf(ped.getTelefono()))
                        .appendQueryParameter("refrencias", String.valueOf(ped.getReferencias()))
                        .appendQueryParameter("nombreusuariof", String.valueOf(ped.getNombreusuario()))
                        ;



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
                    Log.d("paso",resultado.toString());
                    return resultado;

                } else {

                }
            } catch (IOException e) {
                e.printStackTrace()                ;
                Log.d("valorito",e.toString());
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
                Log.d("ii", resultado);


            }else{
                String ii =resultado.toString();
                Log.d("jj", "usuario valido");


                // lanzarsistema();
            }



        }
    }

    public class grabardetalle extends AsyncTask<DetallepedidoRealmFirebase, Void, String> {
        String resultado;
        HttpURLConnection conne;
        URL url = null;
        DetallepedidoRealmFirebase ped;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(DetallepedidoRealmFirebase... params) {
            ped=params[0];
            try {
                url = new URL("https://sodapop.pe/sugest/grabardetallepedidofirebase.php");
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

                Uri.Builder builder = new Uri.Builder()

                        .appendQueryParameter("idproducto",String.valueOf(ped.getIdproductorealm()))
                        .appendQueryParameter("cantidad",String.valueOf(ped.getCantidadrealm()))
                        .appendQueryParameter("precventa",String.valueOf(ped.getPrecventarealm()))
                        .appendQueryParameter("subtotal", String.valueOf(ped.getSubtotal()))
                        .appendQueryParameter("idcalmacen",String.valueOf(ped.getIdalmacenrealm()))
                        .appendQueryParameter("adicionales",String.valueOf(ped.getImagenrealm()))
                        .appendQueryParameter("cremas",String.valueOf(ped.getImagenrealm()))
                        .appendQueryParameter("comentario", String.valueOf(ped.getComentarioacocina()))
                        .appendQueryParameter("ojo", String.valueOf(ped.getOjo()))
                        .appendQueryParameter("imagenreal", String.valueOf(ped.getImagenrealm()))
                        .appendQueryParameter("comentarioacocina", String.valueOf(ped.getComentarioacocina()))
                        .appendQueryParameter("nombreproductorealm", String.valueOf(ped.getNombreproductorealm()))
                        .appendQueryParameter("almacenreal", String.valueOf(ped.getIdalmacenrealm()))
                        ;
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
                    Log.d("paso",resultado.toString());
                    return resultado;

                } else {

                }
            } catch (IOException e) {
                e.printStackTrace()                ;
                Log.d("valorito",e.toString());
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
                Log.d("ii", resultado);


            }else{
                String ii =resultado.toString();
                Log.d("jj", "usuario valido");


                // lanzarsistema();
            }



        }
    }



    public class grabaradicional extends AsyncTask<AdicionalRealmFirebase, Void, String> {
        String resultado;
        HttpURLConnection conne;
        URL url = null;
        AdicionalRealmFirebase ped;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(AdicionalRealmFirebase... params) {
            ped=params[0];
            try {
                url = new URL("https://sodapop.pe/sugest/grabaradicionalfirebase.php");
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

                Uri.Builder builder = new Uri.Builder()

                        .appendQueryParameter("idadicional",String.valueOf(ped.getIdadicional()))
                        .appendQueryParameter("nombreadicional",String.valueOf(ped.getNombreadicional()))
                        .appendQueryParameter("precioadicional",String.valueOf(ped.getPrecioadicional()))
                        .appendQueryParameter("estadoadicional", String.valueOf(ped.getEstadoadicional()))
                        .appendQueryParameter("idproducto",String.valueOf(ped.getIdproducto()))

                        ;
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
                    Log.d("paso",resultado.toString());
                    return resultado;

                } else {

                }
            } catch (IOException e) {
                e.printStackTrace()                ;
                Log.d("valorito",e.toString());
                return null;
            } finally {
                conne.disconnect();
            }
            return resultado;
        }
        @Override
        protected void onPostExecute(String resultado) {

            super.onPostExecute(resultado);





        }
    }



    public class grabarcrema extends AsyncTask<CremaRealmFirebase, Void, String> {
        String resultado;
        HttpURLConnection conne;
        URL url = null;
        CremaRealmFirebase ped;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(CremaRealmFirebase... params) {
            ped=params[0];
            try {
                url = new URL("https://sodapop.pe/sugest/grabarcremafirebase.php");
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

                Uri.Builder builder = new Uri.Builder()

                        .appendQueryParameter("idcrema",String.valueOf(ped.getIdcrema()))
                        .appendQueryParameter("nombrecrema",String.valueOf(ped.getNombrecrema()))
                        .appendQueryParameter("estadocrema",String.valueOf(ped.getEstadocrema()))
                        .appendQueryParameter("iddetalle", String.valueOf(ped.getId()))
                        .appendQueryParameter("idproducto",String.valueOf(ped.getIdproducto()))

                        ;
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
                    Log.d("paso",resultado.toString());
                    return resultado;

                } else {

                }
            } catch (IOException e) {
                e.printStackTrace()                ;
                Log.d("valorito",e.toString());
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
                Log.d("ii", resultado);


            }else{
                String ii =resultado.toString();
                Log.d("jj", "usuario valido");


                // lanzarsistema();
            }



        }
    }
}


