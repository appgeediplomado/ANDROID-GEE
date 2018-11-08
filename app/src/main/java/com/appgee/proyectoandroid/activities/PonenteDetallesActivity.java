package com.appgee.proyectoandroid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.appgee.proyectoandroid.R;
import com.appgee.proyectoandroid.models.Ponente;

public class PonenteDetallesActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView tvNombre;
    TextView tvApellidos;
    TextView tvInstitucion;

    Ponente ponente = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ponente_detalles);

        toolbar = findViewById(R.id.toolbar_other);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //Pone el menu hamburguesa

        tvNombre = findViewById(R.id.tv_nombre_det);
        tvApellidos = findViewById(R.id.tv_apellidos_det);
        tvInstitucion = findViewById(R.id.tv_institucion_det);

        ponente = (Ponente) getIntent().getSerializableExtra("ponente");

        if (ponente != null) {
            tvNombre.setText(ponente.getNombre());
            tvApellidos.setText(ponente.getApellidos());
            tvInstitucion.setText(ponente.getInstitucion());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
