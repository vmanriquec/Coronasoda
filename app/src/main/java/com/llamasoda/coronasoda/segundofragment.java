package com.llamasoda.coronasoda;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.llamasoda.coronasoda.Realm.Detallepedidorealm;
import com.llamasoda.coronasoda.adapter.Adaptadordialogo;
import com.llamasoda.coronasoda.modelo.Datostarjetadialogo;
import com.llamasoda.coronasoda.modelo.Ventas;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.facebook.FacebookSdk.getApplicationContext;

//import android.support.v4.app.Fragment;

public class segundofragment extends Fragment {
    View v;
    String session, nombreususrio, almacenactivo, idalmacenactivo;
    String FileName = "myfile";
    SharedPreferences prefs;
    String[] strArrDataventaso = {"No Suggestions"};
    ArrayList<String> dataListventitas = new ArrayList<String>();
    RecyclerView.Adapter adapterventas;
    public static segundofragment newInstance() {
        return new segundofragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         v= inflater.inflate(R.layout.segundomenudetabs , container, false);



        RecyclerView gggg  = (RecyclerView) v.findViewById(R.id.recydedialogo);
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
        for (int i = 0; i < w; i++){
            int gg=results.get(i).getCantidadrealm();
            int  popo=results.get(i).getIdpedido();
            String lll=results.get(i).getNombreproductorealm();
            Double jjj=Double.parseDouble(results.get(i).getSubtotal());
            Datostarjetadialogo datoso =new Datostarjetadialogo(popo,gg,lll,jjj);
            peopleventas.add(datoso);
        }






    strArrDataventaso = dataListventitas.toArray(new String[dataListventitas.size()]);


        RecyclerView.Adapter  adapterventas = new Adaptadordialogo(peopleventas,getActivity());
     gggg.setLayoutManager(new GridLayoutManager(getActivity(), 1));
     gggg.setAdapter(adapterventas);


        return v;
    }

}
