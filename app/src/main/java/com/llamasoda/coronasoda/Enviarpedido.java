package com.llamasoda.coronasoda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

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
    String almacen,pagocliente,vuelto;
    private static  final String FPEDIDOS="PEDIDOS";
    private static  final String FDETALLEPEDIDO="FDETALLEPEDIDO";
    private static  final String FCREMAS="FCREMAS";
    private static  final String FADICIONAL="FADICIONAL";
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviarpedido);
        prefs = getApplication().getSharedPreferences(FileName, Context.MODE_PRIVATE);
         almacen = prefs.getString("idalmacenactivosf", "");


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


    private void escribiren(int id){
        prefs = getApplication().getSharedPreferences(FileName, Context.MODE_PRIVATE);
        String telefonoguardado = prefs.getString("telefono", "");
        String idfirebase = prefs.getString("idfirebase", "mi fire");
        String direccione=prefs.getString("direccion", "");
        String referencias=prefs.getString("referencia","");
        String latitudp=prefs.getString("latitud","");
        String longitudp=prefs.getString("longitud","");
        String nombredescuento=prefs.getString("nombredescuento","");
        String montodescuento=prefs.getString("montodescuento","");
        String nombrecosto=prefs.getString("nombrecosto","");
        String montocosto=prefs.getString("montocosto","");
        String totalpedido=prefs.getString("totalpedido","");
        String nombreusuariof=prefs.getString("nombreusuariof","");


        pagocliente= prefs.getString("cuantopagacliente", "");
        vuelto=prefs.getString("vuelto", "");








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
            int idclientep=resultsp.get(i).getIdcliente();
            String descripcionpedidop = resultsp.get(i).getDescripcionpedido();
            String estadopedidop = resultsp.get(i).getEstadopedido();
            Double totalp =resultsp.get(i).getTotal();
            int idmesap = resultsp.get(i).getIdmesa();
            String fechapedidop = resultsp.get(i).getFechapedido();
            String idfacebookp = resultsp.get(i).getIdfacebook();
            String  descripcionp = resultsp.get(i).getDescripcionpedido();
            int idusuariop = resultsp.get(i).getIdusuario();

            PedidoRealmFirebase ped = new PedidoRealmFirebase( );
            todoslospedidos.add(ped);
            PedidoRealmFirebase prf =new PedidoRealmFirebase(idpedidop,idclientep,idmesap,Double.parseDouble(totalpedido),"pedido",
                    fechapedidop,idusuariop,idalmacenp,idfacebookp
            ,descripcionpedidop,"1",direccione,idfirebase,nombredescuento,montodescuento,nombrecosto,montocosto,longitudp,latitudp,pagocliente,vuelto,telefonoguardado,referencias,nombreusuariof);

            FirebaseDatabase database =FirebaseDatabase.getInstance();
            DatabaseReference reference=database.getReference(FPEDIDOS);
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
        int idd=results.get(i).getId();
        int idpedido = results.get(i).getIdpedido();
        String nombreproducto = results.get(i).getNombreproductorealm();
        String subtotal = results.get(i).getSubtotal();
        Double precvente =results.get(i).getPrecventarealm();
        int idproductorealm = results.get(i).getIdproductorealm();
        String comentariococina = results.get(i).getComentarioacocina();
       DetallepedidoRealmFirebase det = new DetallepedidoRealmFirebase( idd,1,subtotal,idpedido,cantidad,precvente,nombreproducto,"",Integer.parseInt(almacen),idproductorealm,comentariococina);
        todoslosdetalles.add(det);
    }




int lsrga=todoslosdetalles.size();
ArrayList<CremaRealmFirebase> todaslascremas = new ArrayList<>();


  //      int iddedetalle=todoslosdetalles.get(0).getId();
    ///    for (int i = 0; i < lsrga; i++) {
       /// RealmResults<CremaRealm> resultso =
          //          pedido.where(CremaRealm.class).
           //                 equalTo("id", iddedetalle)
           //                 .findAll();
            //        pedido.beginTransaction();
             //7       pedido.commitTransaction();
        //String nomcrema = resultso.get(i).getNombrecrema();
//CremaRealmFirebase dett = new CremaRealmFirebase( 1,nomcrema,"1",1,1);
  //      todaslascremas.add(dett);
    }








    //int lsrgaa=todoslosdetalles.size();
    //ArrayList<AdicionalRealmFirebase> todosadicional = new ArrayList<>();

    //for (int i = 0; i < lsrga; i++) {
      //  int iddedetalle=todoslosdetalles.get(0).getId();
       // RealmResults<AdicionalRealm> resultsow =
         //       pedido.where(AdicionalRealm.class).
           //             equalTo("id", iddedetalle)
             //           .findAll();



        //pedido.beginTransaction();
        //pedido.commitTransaction();
        //int idi = resultsow.get(i).getId();
        //int idadicional=resultsow.get(i).getIdadicional();
       // int idproa=resultsow.get(i).getIdproducto();
        //Double percadi =Double.valueOf( resultsow.get(i).getPrecioadicional());
        //String nombreadi=resultsow.get(i).getNombreadicional();
        //String gesesatdo=resultsow.get(i).getEstadoadicional();
        //AdicionalRealmFirebase deta = new AdicionalRealmFirebase( idadicional,nombreadi,percadi,gesesatdo,idproa,idi);
        //todosadicional.add(deta);
    }






