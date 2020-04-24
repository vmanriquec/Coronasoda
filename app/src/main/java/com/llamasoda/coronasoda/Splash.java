package com.llamasoda.coronasoda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class Splash extends AppCompatActivity {
    String FileName = "myfile";
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        prefs = getApplication().getSharedPreferences(FileName, Context.MODE_PRIVATE);





        String telefonoguardado = prefs.getString("telefono", "");
        String idfirebase = prefs.getString("idfirebase", "mi fire");
        String direccione=prefs.getString("direccion", "");
        String referencias=prefs.getString("referencia","");
        String latitud=prefs.getString("latitud","");
        String longitud=prefs.getString("longitud","");
        String nombre=prefs.getString("nombreusuariof","");


        if (nombre.equals("")){
            Intent i= new Intent(this,Nuevologin.class);
            startActivity(i);

        }
        else{


            Intent i= new Intent(this,Listaparaseleccionar.class);
            startActivity(i);
        }


        YoYo.with(Techniques.ZoomOutLeft)
                .duration(700)
                .repeat(5)
                .playOn(findViewById(R.id.intro));
    }
}
