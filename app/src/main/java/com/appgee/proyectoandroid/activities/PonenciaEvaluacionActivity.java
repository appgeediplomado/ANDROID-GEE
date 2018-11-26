package com.appgee.proyectoandroid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.appgee.proyectoandroid.R;
import com.appgee.proyectoandroid.models.Ponencia;

public class PonenciaEvaluacionActivity extends BaseActivity {
//    Toolbar toolbar;
    TextView tvPonenciaTitulo;
    Button btnEvaluacionPonenciaCalificar;
    Ponencia ponencia = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_ponencia_evaluacion);

//        toolbar = findViewById(R.id.toolbar_other);
//        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //Pone el menu hamburguesa
        getSupportActionBar().setTitle("Evaluación");

        tvPonenciaTitulo = findViewById(R.id.tvEvaluacionPonenciaTitulo);
        btnEvaluacionPonenciaCalificar = findViewById(R.id.btnEvaluacionPonenciaCalificar);

        ponencia = (Ponencia) getIntent().getSerializableExtra("ponencia");

        if (ponencia != null) {
            tvPonenciaTitulo.setText(ponencia.getTitulo());
            btnEvaluacionPonenciaCalificar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(PonenciaEvaluacionActivity.this, "This is just a demo!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected int getContentAreaLayout() {
        return R.layout.activity_ponencia_evaluacion;
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
