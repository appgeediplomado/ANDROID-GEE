package com.appgee.proyectoandroid.RemoteData;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.appgee.proyectoandroid.models.Ponencia;
import com.appgee.proyectoandroid.webservices.ServerCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VolleyService {
    private Context context;
    private RequestQueue requestQueue = null;
    private static VolleyService service = null;

    private VolleyService(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }

    public static VolleyService getInstance(Context context) {
        if (service == null) {
            service = new VolleyService(context);
        }

        return service;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public <T> void addRequest(Request<T> request) {
        requestQueue.add(request);
    }

    public void getPonencias(final ServerCallback callback) {
        String url = "http://roman.cele.unam.mx/wsgee/trabajos";

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray trabajos = response.getJSONArray("trabajos");
                            ArrayList<Ponencia> ponencias = new ArrayList<>();
                            JSONObject data;

                            for (int i = 0; i < trabajos.length(); i++) {
                                data =  trabajos.getJSONObject(i);

                                Ponencia ponencia = new Ponencia();
                                ponencia.setId(data.getInt("id"));
                                ponencia.setTitulo(data.getString("titulo"));
                                ponencia.setModalidad(data.getString("modalidad"));
                                ponencia.setNombrePonente(data.getString("nombrePonente"));
                                ponencia.setFecha(data.getString("fecha"));
                                ponencia.setHora(data.getString("hora"));
                                ponencia.setLugar(data.getString("lugar"));

                                ponencias.add(ponencia);
                            }

                            callback.onSuccessLista(ponencias);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        VolleyService.getInstance(context).addRequest(request);
    }
}
