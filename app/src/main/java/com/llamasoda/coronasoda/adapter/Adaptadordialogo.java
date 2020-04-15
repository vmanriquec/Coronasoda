package com.llamasoda.coronasoda.adapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.llamasoda.coronasoda.R;

import com.llamasoda.coronasoda.modelo.Datostarjetadialogo;

import java.util.ArrayList;
import java.util.List;

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



    public AdaptadorViewHolder(View v){
        super(v);
        this.cantidad=(TextView) v.findViewById(R.id.cantidadencardview);
        this.productonombre=(TextView) v.findViewById(R.id.productoencardview);
        this.total=(TextView) v.findViewById(R.id.preciototalproductocardview);
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

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

