package com.llamasoda.coronasoda.adapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity.*;
import androidx.recyclerview.widget.RecyclerView;

import com.llamasoda.coronasoda.G;
import com.llamasoda.coronasoda.Inter;
import com.llamasoda.coronasoda.R;

import com.llamasoda.coronasoda.Realm.Crudetallepedido;
import com.llamasoda.coronasoda.Realm.Detallepedidorealm;
import com.llamasoda.coronasoda.Verpedido;
import com.llamasoda.coronasoda.modelo.Datostarjetadialogo;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class Adaptadordialogo extends RecyclerView.Adapter<Adaptadordialogo.AdaptadorViewHolder> {
private Context mainContext;
        String foto;
        SharedPreferences prefs;
        String FileName ="myfile";
private List<Datostarjetadialogo> items;
public Adaptadordialogo(ArrayList<Datostarjetadialogo> items, Context contexto){
        this.mainContext=contexto;
        this.items=items;


        }
static class AdaptadorViewHolder extends RecyclerView.ViewHolder{
    protected TextView cantidad;
    protected TextView productonombre;
    protected TextView total;
    protected Button eliminar;



    public AdaptadorViewHolder(View v){
        super(v);
        this.cantidad=(TextView) v.findViewById(R.id.cantidadencardview);
        this.productonombre=(TextView) v.findViewById(R.id.productoencardview);
        this.total=(TextView) v.findViewById(R.id.preciototalproductocardview);
        this.eliminar=(Button) v.findViewById(R.id.eliminardedialog);
    }
}


    @Override
    public Adaptadordialogo.AdaptadorViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.tarjetamuestraendialogopedidos,viewGroup,false);
        return new Adaptadordialogo.AdaptadorViewHolder(v);
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final Adaptadordialogo.AdaptadorViewHolder viewHolder, final int position) {
        final Datostarjetadialogo item = items.get(position);
        viewHolder.itemView.setTag(item);



        viewHolder.cantidad.setText(String.valueOf(item.getCantidad()));
        viewHolder.productonombre.setText(item.getProducto());
        viewHolder.total.setText(String.valueOf(item.getTotal()));

        viewHolder.eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tr= Crudetallepedido.calculateIndex();
                int iddedetalles=Crudetallepedido.calculateIndex();
                G.eliminaraTotalcrema(iddedetalles);
                G.eliminarTotaladicional(iddedetalles);
                G.eliminarTOTALdetallepedido(iddedetalles);
                items.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, getItemCount());


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

               // listener.foo(String.valueOf(w));


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
              //  tio.setText(String.valueOf(tt));

                ///gggg.
                //int position = gggg.indexOf(cart);
                //itemsList.remove(position);
                //notifyItemRemoved(position);
                //notifyItemRangeChanged(position, itemsList.size());

            }
        });
    }



    @Override
    public int getItemCount() {
        return items.size();
    }

}

