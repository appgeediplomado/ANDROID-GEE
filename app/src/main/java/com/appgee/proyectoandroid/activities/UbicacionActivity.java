package com.appgee.proyectoandroid.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.appgee.proyectoandroid.R;

public class UbicacionActivity extends BaseActivity {
    TextView tvEdificio;
    TextView tvUbicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //Pone el menu hamburguesa

        tvEdificio = findViewById(R.id.tvNombreEdificio);
        tvUbicacion = findViewById(R.id.tvNombreUbicacion);

        Intent recibir = getIntent();
        tvEdificio.setText( recibir.getStringExtra("Edificio"));
        tvUbicacion.setText( recibir.getStringExtra("TituloUbicacion") );

    }

    @Override
    protected int getContentAreaLayout() {
        return R.layout.activity_ubicacion;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
