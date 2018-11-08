package com.appgee.proyectoandroid.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.appgee.proyectoandroid.R;

public class UbicacionActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tvEdificio;
    TextView tvUbicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);

        toolbar = findViewById(R.id.toolbar_ubicacion);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //Pone el menu hamburguesa

        tvEdificio = findViewById(R.id.tvNombreEdificio);
        tvUbicacion = findViewById(R.id.tvNombreUbicacion);

        Intent recibir = getIntent();
        tvEdificio.setText( recibir.getStringExtra("Edificio"));
        tvUbicacion.setText( recibir.getStringExtra("TituloUbicacion") );

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
