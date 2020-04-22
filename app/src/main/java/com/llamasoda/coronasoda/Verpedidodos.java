package com.llamasoda.coronasoda;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.llamasoda.coronasoda.Realm.Detallepedidorealm;
import com.llamasoda.coronasoda.adapter.Adaptadordialogo;
import com.llamasoda.coronasoda.modelo.Datostarjetadialogo;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class Verpedidodos extends AppCompatActivity  {
    String[] strArrDataventaso = {"No Suggestions"};
    ArrayList<String> dataListventitas = new ArrayList<String>();
    RecyclerView gggg;
    Context context;
    TextView  frito;
    TextView tio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verpedido);
     frito =(TextView) findViewById(R.id.num);
        tio=(TextView) findViewById(R.id.miavion);


        Realm.init(getApplication());
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("pedido.realm")
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(realmConfig);


        TextView yy=(TextView) findViewById(R.id.txtdireccion);

        yy.setText("avenida la petit");






        recargartotalesisisomos();

    }

    public  void recargartotalesisisomos() {


        ArrayList<Datostarjetadialogo> peopleventas = new ArrayList<>();
        peopleventas.clear();
        ArrayList<Datostarjetadialogo> datosdetodaslastarjetas = new ArrayList<>();
        ArrayList<String> datalisttarjeta = new ArrayList<String>();
        String[] strtarjeta = {"No Suggestions"};
        datosdetodaslastarjetas.clear();

        Realm pedido = Realm.getDefaultInstance();
        RealmResults<Detallepedidorealm> results =
                pedido.where(Detallepedidorealm.class)
                        .findAll();
        int w = results.size();

        Double tt=0.0;
        for (int i = 0; i < w; i++){
            int gg=results.get(i).getCantidadrealm();
            int  popo=results.get(i).getIdpedido();
            String lll=results.get(i).getNombreproductorealm();
            Double jjj=Double.parseDouble(results.get(i).getSubtotal());
            tt=tt+jjj;
            Datostarjetadialogo datoso =new Datostarjetadialogo(popo,gg,lll,jjj);
            peopleventas.add(datoso);
        }
        frito.setText(String.valueOf(w));
        tio.setText(String.valueOf(tt));
        strArrDataventaso = dataListventitas.toArray(new String[dataListventitas.size()]);
        Adapter  adapterventas = new Adaptadordialogo(peopleventas,this);
        RecyclerView gggg  = (RecyclerView) findViewById(R.id.recyclerdepedidos);
        gggg.setLayoutManager(new LinearLayoutManager(this));
        gggg.setAdapter(adapterventas);





    }



    public void onEventName(String k) {
        context = this;

        frito.setText(k);


    }
    private void cargarbarradeabajo() {
        TextView tot=(TextView) findViewById(R.id.seis);
        TextView cantidadfragment=(TextView) findViewById(R.id.cuatro);

        Realm.init(getApplicationContext());
        Realm pedido = Realm.getDefaultInstance();
        RealmResults<Detallepedidorealm> results =
                pedido.where(Detallepedidorealm.class)
                        .findAll();
        int w = results.size();
        cantidadfragment.setText(String.valueOf(w));
        Double tt=0.0;
        for (int i = 0; i < w; i++){
            int gg=results.get(i).getCantidadrealm();
            int  popo=results.get(i).getIdpedido();
            String lll=results.get(i).getNombreproductorealm();
            Double jjj=Double.parseDouble(results.get(i).getSubtotal());
            tt=tt+jjj;

        }
        tot.setText("S/. "+String.valueOf(tt));



    }
}
