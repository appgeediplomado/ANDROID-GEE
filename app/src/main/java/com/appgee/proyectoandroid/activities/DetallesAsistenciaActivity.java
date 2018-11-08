package com.appgee.proyectoandroid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.appgee.proyectoandroid.R;

public class DetallesAsistenciaActivity extends AppCompatActivity {

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_asistencia);

        toolbar = findViewById(R.id.toolbar_detalles_asistencia);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
