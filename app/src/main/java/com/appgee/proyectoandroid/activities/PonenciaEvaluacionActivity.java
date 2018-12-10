package com.appgee.proyectoandroid.activities;

import android.app.DownloadManager;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.appgee.proyectoandroid.R;
import com.appgee.proyectoandroid.RemoteData.VolleyService;
import com.appgee.proyectoandroid.Session.SessionGee;
import com.appgee.proyectoandroid.db.AppDatabase;
import com.appgee.proyectoandroid.db.Interactor;
import com.appgee.proyectoandroid.models.Ponencia;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PonenciaEvaluacionActivity extends BaseActivity {
    TextView tvPonenciaTitulo;
    Button btnEvaluacionPonenciaCalificar;
    RatingBar rbCalidadPonencia;
    RatingBar rbExperienciaPonente;
    RatingBar rbRelevanciaPonencia;
    Ponencia ponencia = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //Pone el menu hamburguesa
        getSupportActionBar().setTitle("Evaluación");

        tvPonenciaTitulo = findViewById(R.id.tvEvaluacionPonenciaTitulo);
        btnEvaluacionPonenciaCalificar = findViewById(R.id.btnEvaluacionPonenciaCalificar);
        rbCalidadPonencia = findViewById(R.id.rbCalidadPonencia);
        rbExperienciaPonente = findViewById(R.id.rbExperienciaPonente);
        rbRelevanciaPonencia = findViewById(R.id.rbRelevanciaPonencia);

        ponencia = (Ponencia) getIntent().getSerializableExtra("ponencia");

        if (ponencia != null) {
            tvPonenciaTitulo.setText(ponencia.getTitulo());
            rbCalidadPonencia.setRating(ponencia.getCalidadPonencia());
            rbExperienciaPonente.setRating(ponencia.getExperienciaPonente());
            rbRelevanciaPonencia.setRating(ponencia.getRelevanciaPonencia());

            btnEvaluacionPonenciaCalificar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SessionGee sesion = new SessionGee(getBaseContext());

                    if (!sesion.usuarioTieneSesionActiva()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(PonenciaEvaluacionActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
                        builder.setTitle("Error")
                                .setMessage("Para evaluar una ponencia, primero debe iniciar sesión")
                                .setCancelable(false)
                                .setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    } else {
                        StringRequest request = new StringRequest(
                                Request.Method.POST,
                                "http://roman.cele.unam.mx/wsgee/asistentes/" + sesion.getUsuarioId() + "/retroalimentacion/" + ponencia.getId(),
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        // Guardar la retroalimentación localmente
                                        ponencia.setCalidadPonencia((int) rbCalidadPonencia.getRating());
                                        ponencia.setExperienciaPonente((int) rbExperienciaPonente.getRating());
                                        ponencia.setRelevanciaPonencia((int) rbRelevanciaPonencia.getRating());
                                        Interactor.crearBD(PonenciaEvaluacionActivity.this);
                                        Interactor.guardarPonencia(ponencia);

                                        AlertDialog.Builder builder = new AlertDialog.Builder(PonenciaEvaluacionActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
                                        builder.setTitle("GEE")
                                                .setMessage("Muchas gracias por su retroalimentación")
                                                .setCancelable(false)
                                                .setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });
                                        AlertDialog alert = builder.create();
                                        alert.show();
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }
                        ){
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String,String> params = new HashMap<String, String>();
                                params.put("ponencia", rbCalidadPonencia.getRating() + "");
                                params.put("ponente", rbExperienciaPonente.getRating() + "");
                                params.put("relevancia", rbRelevanciaPonencia.getRating() + "");

                                return params;
                            }

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String,String> params = new HashMap<String, String>();
                                params.put("Content-Type","application/x-www-form-urlencoded");

                                return  params;
                            }
                        };

                        VolleyService.getInstance(PonenciaEvaluacionActivity.this).addRequest(request);
                    }
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
