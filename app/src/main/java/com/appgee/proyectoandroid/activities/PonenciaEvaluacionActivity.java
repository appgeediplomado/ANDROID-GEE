package com.appgee.proyectoandroid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.appgee.proyectoandroid.R;
import com.appgee.proyectoandroid.models.Ponencia;

public class PonenciaEvaluacionActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView tvPonenciaTitulo;
    Ponencia ponencia = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ponencia_evaluacion);

        toolbar = findViewById(R.id.toolbar_other);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //Pone el menu hamburguesa
        getSupportActionBar().setTitle("Evaluación");

        tvPonenciaTitulo = findViewById(R.id.tvEvaluacionPonenciaTitulo);

        ponencia = (Ponencia) getIntent().getSerializableExtra("ponencia");

        if (ponencia != null) {
            tvPonenciaTitulo.setText(ponencia.getTitulo());
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
