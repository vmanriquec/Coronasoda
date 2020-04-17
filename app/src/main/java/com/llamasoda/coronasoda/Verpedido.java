package com.llamasoda.coronasoda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.llamasoda.coronasoda.Realm.Detallepedidorealm;
import com.llamasoda.coronasoda.adapter.Adaptadordialogo;
import com.llamasoda.coronasoda.modelo.Datostarjetadialogo;

import org.w3c.dom.Text;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class Verpedido extends AppCompatActivity {
    String[] strArrDataventaso = {"No Suggestions"};
    ArrayList<String> dataListventitas = new ArrayList<String>();

    public Verpedido(Verpedido verpedido) {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verpedido);

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

    private void recargartotalesisisomos() {
        RecyclerView gggg  = (RecyclerView) findViewById(R.id.recyclerdepedidos);
        TextView io=(TextView) findViewById(R.id.num);
        TextView tio=(TextView) findViewById(R.id.miavion);

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
        io.setText(String.valueOf(w));
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
        tio.setText(String.valueOf(tt));
        strArrDataventaso = dataListventitas.toArray(new String[dataListventitas.size()]);
        RecyclerView.Adapter  adapterventas = new Adaptadordialogo(peopleventas,getApplication());
        gggg.setLayoutManager(new GridLayoutManager(getApplication(), 1));
        gggg.setAdapter(adapterventas);


    }




}
