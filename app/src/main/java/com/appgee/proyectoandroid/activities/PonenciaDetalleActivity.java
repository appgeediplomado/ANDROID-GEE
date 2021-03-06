package com.appgee.proyectoandroid.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.appgee.proyectoandroid.R;
import com.appgee.proyectoandroid.Utils.Utils;
import com.appgee.proyectoandroid.db.Interactor;
import com.appgee.proyectoandroid.models.Ponencia;
import com.appgee.proyectoandroid.webservices.ServerCallback;

import java.util.ArrayList;

public class PonenciaDetalleActivity extends BaseActivity {
    TextView tvTituloPonencia;
    TextView tvNombrePonente;
    TextView tvFechaPonencia;
    TextView tvHoraPonencia;
    TextView tvLugarPonencia;
    Button btnAgendarPonencia;
    Button btnCalificarPonencia;
    TextView tvSinopsis;

    Ponencia ponencia = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //Pone el menu hamburguesa

        tvTituloPonencia = findViewById(R.id.tvTituloPonencia);
        tvNombrePonente = findViewById(R.id.tvNombrePonente);
        tvFechaPonencia = findViewById(R.id.tvFechaPonencia);
        tvHoraPonencia = findViewById(R.id.tvHoraPonencia);
        tvLugarPonencia = findViewById(R.id.tvLugarPonencia);
        btnAgendarPonencia = findViewById(R.id.btnAgendarPonencia);
        btnCalificarPonencia = findViewById(R.id.btnCalificarPonencia);
        tvSinopsis =  findViewById(R.id.tvAbstractPonencia);

        ponencia = (Ponencia) getIntent().getSerializableExtra("ponencia");

        if (ponencia != null) {
            tvTituloPonencia.setText(ponencia.getTitulo());
            tvNombrePonente.setText(ponencia.getNombrePonente());
            tvFechaPonencia.setText(ponencia.getFecha());
            tvHoraPonencia.setText(ponencia.getHora());
            tvLugarPonencia.setText(ponencia.getLugar());

            Interactor.obtenerSinopsis(ponencia.getId(), this, new ServerCallback<Ponencia>() {
                @Override
                public void onSuccessLista(ArrayList<Ponencia> lista) {
                    if (lista.size() > 0) {
                        Ponencia ponencia = lista.get(0);
                        tvSinopsis.setText(ponencia.getSinopsis());
                    }
                }
            });

            btnAgendarPonencia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.agendarPonencia(ponencia, v.getContext());
                }
            });

            btnCalificarPonencia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(PonenciaDetalleActivity.this, PonenciaEvaluacionActivity.class);
                    intent.putExtra("ponencia", ponencia);
                    startActivityForResult(intent, 84);
                }
            });
        }
    }

    @Override
    protected int getContentAreaLayout() {
        return R.layout.activity_ponencia_detalle;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK  && requestCode == 84) {
            this.ponencia = (Ponencia) data.getExtras().getSerializable("ponencia");
            Intent intent = getIntent();
            intent.putExtra("ponencia", ponencia);

            this.setResult(RESULT_OK, intent);
        }  else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
