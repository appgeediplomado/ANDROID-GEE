package com.appgee.proyectoandroid.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.appgee.proyectoandroid.R;
import com.appgee.proyectoandroid.models.Ponencia;

public class PonenciaDetalleActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView tvTituloPonencia;
    TextView tvNombrePonente;
    TextView tvFechaPonencia;
    TextView tvHoraPonencia;
    TextView tvLugarPonencia;
    Button btnCalificarPonencia;

    Ponencia ponencia = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ponencia_detalle);

        toolbar = findViewById(R.id.toolbar_other);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //Pone el menu hamburguesa
        getSupportActionBar().setTitle("Actividad");

        tvTituloPonencia = findViewById(R.id.tvTituloPonencia);
        tvNombrePonente = findViewById(R.id.tvNombrePonente);
        tvFechaPonencia = findViewById(R.id.tvFechaPonencia);
        tvHoraPonencia = findViewById(R.id.tvHoraPonencia);
        tvLugarPonencia = findViewById(R.id.tvLugarPonencia);
        btnCalificarPonencia = findViewById(R.id.btnCalificarPonencia);

        ponencia = (Ponencia) getIntent().getSerializableExtra("ponencia");

        if (ponencia != null) {
            tvTituloPonencia.setText(ponencia.getTitulo());
            tvNombrePonente.setText(ponencia.getNombrePonente());
            tvFechaPonencia.setText(ponencia.getFecha());
            tvHoraPonencia.setText(ponencia.getHora());
            tvLugarPonencia.setText(ponencia.getLugar());

            btnCalificarPonencia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(PonenciaDetalleActivity.this, PonenciaEvaluacionActivity.class);
                    intent.putExtra("ponencia", ponencia);
                    startActivity(intent);
                }
            });
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
