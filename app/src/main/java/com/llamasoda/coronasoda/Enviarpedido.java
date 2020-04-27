package com.llamasoda.coronasoda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.llamasoda.coronasoda.Realm.AdicionalRealm;
import com.llamasoda.coronasoda.Realm.AdicionalRealmFirebase;
import com.llamasoda.coronasoda.Realm.CremaRealm;
import com.llamasoda.coronasoda.Realm.CremaRealmFirebase;
import com.llamasoda.coronasoda.Realm.Crudpedido;
import com.llamasoda.coronasoda.Realm.DetallepedidoRealmFirebase;
import com.llamasoda.coronasoda.Realm.Detallepedidorealm;
import com.llamasoda.coronasoda.Realm.PedidoRealm;
import com.llamasoda.coronasoda.Realm.PedidoRealmFirebase;

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
            String estadopedidop = resultsp.get(i).getEstadopedido();
            Double totalp = resultsp.get(i).getTotal();
            int idmesap = resultsp.get(i).getIdmesa();
            String fechapedidop = resultsp.get(i).getFechapedido();
            String idfacebookp = resultsp.get(i).getIdfacebook();
            String descripcionp = resultsp.get(i).getDescripcionpedido();
            int idusuariop = resultsp.get(i).getIdusuario();

            PedidoRealmFirebase ped = new PedidoRealmFirebase();
            todoslospedidos.add(ped);

            PedidoRealmFirebase prf = new PedidoRealmFirebase(idpedidop, idclientep, idmesap, Double.parseDouble(totalpedido), "pedido",
                    fechapedidop, idusuariop, idalmacenp, idfacebookp
                    , descripcionpedidop, "1", direccione, idfirebase, nombredescuento, montodescuento, nombrecosto, montocosto, longitudp, latitudp, pagocliente, vuelto, telefonoguardado, referencias, nombreusuariof);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference(FPEDIDOS);
            reference.child(idfirebase).setValue(prf);

        }


        ArrayList<DetallepedidoRealmFirebase> todoslosdetalles = new ArrayList<>();
        //sacar el todoslosdetalles
        RealmResults<Detallepedidorealm> results =
                pedido.where(Detallepedidorealm.class)
                        .findAll();
        int w = results.size();
        for (int i = 0; i < w; i++) {
            int cantidad = results.get(i).getCantidadrealm();
            int idd = results.get(i).getId();
            int idpedido = results.get(i).getIdpedido();
            String nombreproducto = results.get(i).getNombreproductorealm();
            String subtotal = results.get(i).getSubtotal();
            Double precvente = results.get(i).getPrecventarealm();
            int idproductorealm = results.get(i).getIdproductorealm();
            String comentariococina = results.get(i).getComentarioacocina();
            DetallepedidoRealmFirebase det = new DetallepedidoRealmFirebase(idd, 1, subtotal, idfirebase, cantidad, precvente, nombreproducto, "", Integer.parseInt(almacen), idproductorealm, comentariococina);
            todoslosdetalles.add(det);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference(FPEDIDOS);
            String pppp = String.valueOf(det.getIdproductorealm() + i);
            reference.child(idfirebase).child(pppp).setValue(det);

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
                    String pppo = String.valueOf(dett.getIdcrema());
                    referenceec.child(idfirebase).child(pppp).child(pppo).setValue(dett);
                    }

        //lleno adicionales
            RealmResults<AdicionalRealm> resulcrema =
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
                String pppo = String.valueOf(dett.getIdcrema());
                referenceec.child(idfirebase).child(pppp).child(pppo).setValue(dett);
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

        RealmResults<CremaRealm> results =
                pedido.where(CremaRealm.class)

                        .findAll();

        results.deleteAllFromRealm() ;
        pedido.commitTransaction();
        return results;
    }
    public final static List<AdicionalRealm> Eliminartotaladicionals() {
        Realm pedido = Realm.getDefaultInstance();

        RealmResults<AdicionalRealm> results =
                pedido.where(AdicionalRealm.class)

                        .findAll();

        pedido.beginTransaction();
        results.deleteAllFromRealm();     // App crash
        pedido.commitTransaction();
        return results;
    }

    public final static List<Detallepedidorealm> Eliminartotaldetalles() {
        Realm pedido = Realm.getDefaultInstance();

        RealmResults<Detallepedidorealm> results =
                pedido.where(Detallepedidorealm.class)
                        .findAll();

        pedido.beginTransaction();
        results.deleteAllFromRealm();     // App crash
        pedido.commitTransaction();
        return results;
    }
    public final static List<PedidoRealm> Eliminartotalpedidos() {
        Realm pedido = Realm.getDefaultInstance();

        RealmResults<PedidoRealm> results =
                pedido.where(PedidoRealm.class)
                        .findAll();
        pedido.beginTransaction();
        results.deleteAllFromRealm();     // App crash
        return results;
    }

}


