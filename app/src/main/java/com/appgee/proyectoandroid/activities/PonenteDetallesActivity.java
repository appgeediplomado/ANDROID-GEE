package com.appgee.proyectoandroid.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.appgee.proyectoandroid.R;
import com.appgee.proyectoandroid.db.Interactor;
import com.appgee.proyectoandroid.models.Ponente;
import com.appgee.proyectoandroid.webservices.ServerCallback;

import java.util.ArrayList;

public class PonenteDetallesActivity extends BaseActivity {
    TextView tvNombre;
    TextView tvApellidos;
    TextView tvInstitucion;
    TextView tvBiodata;

    Ponente ponente = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //Pone el menu hamburguesa
        getSupportActionBar().setTitle("Ponente");

        tvNombre = findViewById(R.id.tv_nombre_det);
        tvApellidos = findViewById(R.id.tv_apellidos_det);
        tvInstitucion = findViewById(R.id.tv_institucion_det);
        tvBiodata = findViewById(R.id.tv_biodata);

        ponente = (Ponente) getIntent().getSerializableExtra("ponente");

        if (ponente != null) {
            tvNombre.setText(ponente.getNombre());
            tvApellidos.setText(ponente.getApellidos());
            tvInstitucion.setText(ponente.getInstitucion());
            tvBiodata.setText("");
        }

        //Se ejecuta asyncronramente la recuperación de los ponentes
        Interactor.obtenerPonentes(this.getBaseContext(), new ServerCallback<Ponente>() {
            @Override
            public void onSuccessLista(ArrayList<Ponente> lista) {
                Log.i("PONENTES_ACTIVITY_DET", "Callback de ponentes");
                //Recibimos la lista de ponentes
                Ponente registro = lista.get(0);
                Log.i("PONENTES_CHECK_DET", registro.toString());
                Log.i("PONENTES_CHECK_BIO", registro.getBiodata());
                tvBiodata.setText(registro.getBiodata());
            }
        }, ponente.getId());
    }

    @Override
    protected int getContentAreaLayout() {
        return R.layout.activity_ponente_detalles;
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
