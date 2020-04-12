package com.llamasoda.coronasoda;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.llamasoda.coronasoda.adapter.Adaptadorproductos;
import com.llamasoda.coronasoda.modelo.Productos;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.llamasoda.coronasoda.Login.CONNECTION_TIMEOUT;
import static com.llamasoda.coronasoda.Login.READ_TIMEOUT;

//import android.support.v4.app.Fragment;

public class primerfragmentoparamenu extends Fragment {
View view;
    String session, nombreususrio, almacenactivo, idalmacenactivo;
    String FileName = "myfile";
    SharedPreferences prefs;
    private String[] strArrData = {"No Suggestions"};
    private String[] strArrDataventas = {"No Suggestions"};
    private String[] strArrDataproducto = {"No Suggestions"};
    private String[] strArrDataproductopedido = {"No Suggestions"};
    private String[] strArrDatarecibe = {"No Suggestions"};
    private String[] strArrDatamovimientos = {"No Suggestions"};


    private RecyclerView recycler;
    private RecyclerView.Adapter adapterproducto;
    private RecyclerView.LayoutManager lManager;
   ArrayList<String> dataListproducto = new ArrayList<String>();
    Productos mesoproducto;
    private RecyclerView recyclerproducto;
    ArrayList<Productos> peopleproducto = new ArrayList<>();
  public static primerfragmentoparamenu newInstance() {
        return new primerfragmentoparamenu();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.pimerfragmendemenu , container, false);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        TextView fechadehoy = (TextView) view.findViewById(R.id.fechaactual);
        String currentDateandTime = sdf.format(new Date());
        fechadehoy.setText(currentDateandTime);
        TextView almacen = (TextView) view.findViewById(R.id.almacenactivo);
        ListView lista=(ListView) view.findViewById(R.id.listainicio);
        recyclerproducto=(RecyclerView) view.findViewById(R.id.recyclerlistado);

        recyclerproducto.setHasFixedSize(true);
        recyclerproducto.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        int numberOfColumns = 6;
        recyclerproducto.setHasFixedSize(true);
        lManager = new LinearLayoutManager(getContext());
        recyclerproducto.setLayoutManager(lManager);

startASycnc();



        return view;
    }


    public void startASycnc() {
        new traerproductos().execute("1");
    }

    private class traerproductos extends AsyncTask<String, String, String> {
        HttpURLConnection conne;
        URL url = null;
        ArrayList<Productos> listaalmaceno = new ArrayList<Productos>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL("https://sodapop.pe/sugest/apitraerproductosmaestra.php");
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

                        .appendQueryParameter("idalmacen", params[0]);

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
            peopleproducto.clear();
            if (result.equals("no rows")) {
            } else {
                try {
                    JSONArray jArray = new JSONArray(result);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.optJSONObject(i);
                        mesoproducto = new Productos(json_data.getInt("idproducto"), json_data.getString("nombreproducto"), json_data.getString("estadoproducto"), json_data.getString("ingredientes"), json_data.getDouble("precventa"), json_data.getString("descripcion"));
                        peopleproducto.add(mesoproducto);
                    }
                    strArrDataproducto = dataListproducto.toArray(new String[dataListproducto.size()]);
                    adapterproducto = new Adaptadorproductos(peopleproducto, getContext());
                    recyclerproducto.setLayoutManager(new GridLayoutManager(getContext(), 1));
                    recyclerproducto.setAdapter(adapterproducto);
                } catch (JSONException e) {
                    Log.d("erroro",e.toString());
                }
            }
        }
    }

}