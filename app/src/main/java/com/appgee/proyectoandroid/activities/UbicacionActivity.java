package com.appgee.proyectoandroid.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appgee.proyectoandroid.R;

public class UbicacionActivity extends BaseActivity {
    TextView tvEdificio;
    TextView tvUbicacion;
    ImageView ivUbicacion;

    static final int EDIFICIO_A_PLANTA_BAJA = 1;
    static final int EDIFICIO_A_PRIMER_PISO = 2;
    static final int EDIFICIO_A_TECER_PISO = 3;
    static final int EDIFICIO_B_BASAMENTO = 4;
    static final int EDIFICIO_B_PLANTA_BAJA = 5;
    static final int EDIFICIO_B_PRIMER_PISO = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //Pone el menu hamburguesa

        tvEdificio = findViewById(R.id.tvNombreEdificio);
        tvUbicacion = findViewById(R.id.tvNombreUbicacion);
        ivUbicacion = findViewById(R.id.ivUbicacion);

        Intent recibir = getIntent();
        tvEdificio.setText( recibir.getStringExtra("Edificio"));
        tvUbicacion.setText( recibir.getStringExtra("TituloUbicacion") );
        int im = recibir.getIntExtra("ImgId",0);
        switch (im)
        {
            case EDIFICIO_A_PLANTA_BAJA: ivUbicacion.setImageResource(R.drawable.edifico_a_planta_baja);
            break;
            case EDIFICIO_A_PRIMER_PISO: ivUbicacion.setImageResource(R.drawable.edifico_a_primer_piso);
            break;
            case EDIFICIO_A_TECER_PISO: ivUbicacion.setImageResource(R.drawable.edifico_a_tercer_piso);
            break;
            case EDIFICIO_B_BASAMENTO: ivUbicacion.setImageResource(R.drawable.edifico_b_basamento);
            break;
            case EDIFICIO_B_PLANTA_BAJA: ivUbicacion.setImageResource(R.drawable.edifico_b_planta_baja);
            break;
            case EDIFICIO_B_PRIMER_PISO: ivUbicacion.setImageResource(R.drawable.edifico_b_primer_piso);
            break;
        }
        //ivUbicacion.setImageResource(this.getResources().getIdentifier(recibir.getStringExtra("Img"),"drawable", getPackageName()));
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
