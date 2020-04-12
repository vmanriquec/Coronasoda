package com.llamasoda.coronasoda.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.llamasoda.coronasoda.G;
import com.llamasoda.coronasoda.R;
import com.llamasoda.coronasoda.modelo.Adicional;
import com.llamasoda.coronasoda.modelo.Detallepedido;
import com.llamasoda.coronasoda.modelo.Productos;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import jp.wasabeef.picasso.transformations.CropSquareTransformation;
public class Adaptadorproductos extends RecyclerView.Adapter<Adaptadorproductos.AdaptadorViewHolder> {
    private Context mainContext;
    String foto;
    SharedPreferences prefs;
    String FileName ="myfile",productocabecera;
    private List<Productos> items;
    ArrayList<Detallepedido> detallepedido=new ArrayList<>();
    Detallepedido objdetallepedido;
    ArrayList<Adicional> peopleadicional = new ArrayList<>();
    String[] stradicional = {"No Suggestions"};
    ArrayList<String> datalistadicional = new ArrayList<String>();
    public Adaptadorproductos(List<Productos> items, Context contexto){
        this.mainContext=contexto;
        this.items=items;
    }
    static class AdaptadorViewHolder extends RecyclerView.ViewHolder{
        protected TextView productonombre;
        protected TextView idproducto;
        protected TextView precio;
        protected TextView cantidadpedida;
        protected ImageView imagen;
        protected TextView productoingredientes, inventario;
        protected Button mas;

        public AdaptadorViewHolder(View v){
            super(v);
            this.idproducto=(TextView) v.findViewById(R.id.idproductop2);
            this.productonombre=(TextView) v.findViewById(R.id.nombre);
            this.mas=(Button)v.findViewById(R.id.botonmas1);

            this.precio=(TextView) v.findViewById(R.id.precio);
            this.productoingredientes=(TextView)v.findViewById(R.id.ingredientes);
            this.inventario=(TextView)v.findViewById(R.id.inventario);
            this.imagen=(ImageView)v.findViewById(R.id.imagen);
            this.inventario=(TextView)v.findViewById(R.id.inventario);
        }
    }
    @Override
    public Adaptadorproductos.AdaptadorViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.tarjetamuestraproducto,viewGroup,false);
        return new Adaptadorproductos.AdaptadorViewHolder(v);
    }
    @Override
    public void onBindViewHolder(final Adaptadorproductos.AdaptadorViewHolder viewHolder, final int position) {

        final Productos item = items.get(position);
        viewHolder.itemView.setTag(item);

        viewHolder.productonombre.setText(item.getNombreproducto());
        productocabecera=item.getNombreproducto();
        viewHolder.idproducto.setText(String.valueOf(item.getIdproducto()));
viewHolder.productoingredientes.setText(String.valueOf(item.getIngredientes()));
        viewHolder.precio.setText("S/. " + String.valueOf(item.getPrecventa()));
        viewHolder.inventario.setText((String.valueOf(item.getEstadoproducto())));
        foto = item.getDescripcion().toString();
        Picasso.get().load(foto).transform(new CropSquareTransformation()).resize(80, 80)
                .into(viewHolder.imagen);
        // viewHolder.mas.setVisibility(View.GONE);
        //viewHolder.menos.setVisibility(View.GONE);
        viewHolder.mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), G.class);
                intent.putExtra("nombredeproductoseleccionado",item.getNombreproducto());
                intent.putExtra("preciodeproductoseleccionado",String.valueOf(item.getPrecventa()));
                intent.putExtra("idproductoseleccionado",String.valueOf(item.getIdproducto()));
                v.getContext().startActivity(intent);



                //AlertDialog.Builder builder = new AlertDialog.Builder((Activity) v.getContext());
// Show the alert


                // String cantidad=viewHolder.cantidadpedida.getText().toString();
                //int c= Integer.parseInt(cantidad);
                //if(c>=0){
                ///  c=c+1;viewHolder.cantidadpedida.setText( String.valueOf(c));

                // Toast.makeText(getApplicationContext(),item.getNombreproducto()+viewHolder.cantidadpedida.getText(),Toast.LENGTH_LONG).show();

                //         }
                //  AlertDialog.Builder alertDialog = new AlertDialog.Builder((Activity) v.getContext());


            }
        });


        /*si esta check activo para aumentar cantidad*/







    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
