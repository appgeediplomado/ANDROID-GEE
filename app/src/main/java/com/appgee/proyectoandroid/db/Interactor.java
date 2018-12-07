package com.appgee.proyectoandroid.db;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.appgee.proyectoandroid.RemoteData.VolleyService;
import com.appgee.proyectoandroid.models.Ponencia;
import com.appgee.proyectoandroid.models.Ponente;
import com.appgee.proyectoandroid.webservices.ServerCallback;
import com.appgee.proyectoandroid.webservices.WSSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Interactor {

    private final static String DB_NAME = "gee_db";
    //our app database object
    private static AppDatabase appDatabase;

    private static ArrayList<Ponente> ponentes = new ArrayList<>();

    public static void crearBD(Context context) {
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, DB_NAME).build();
    }

    public static void obtenerPonencias(Context context, final ServerCallback<Ponencia> callback) {
        VolleyService.getInstance(context).getPonencias(new ServerCallback<Ponencia>() {
            @Override
            public void onSuccessLista(ArrayList<Ponencia> lista) {
                // Agregar ponencias a la bd local
                // updatePonencias(lista);
                callback.onSuccessLista(lista);
            }
        });
    }

    /**
     * Método que consulta los ponentes del webservice o de la BD
     * Basado en:
     * //http://www.sanktips.com/2017/12/10/how-to-fetch-json-data-using-volley-and-display-it-to-recyclerview-in-android-studio/
     *
     * @param context Contexto de la actividad, para poder mostrar un Toast en caso de error
     * @param callback Interfaz que devuelve la lista de ponentes y se ejecuta en el fragment despues del onResponse
     */
    public static void obtenerPonentes(final Context context, final ServerCallback<Ponente> callback) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                if (hayNuevosPonentes()) {
                    Log.i("PONENTES_WS", "Se consultara el WebService");
                    String url = "http://roman.cele.unam.mx/wsgee/ponentes";

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                //final ArrayList<Ponente> ponentes = new ArrayList<>();
                                JSONObject jsonObject = response;
                                JSONArray jsonArray = jsonObject.getJSONArray("ponentes");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jo = jsonArray.getJSONObject(i);

                                    ponentes.add(new Ponente(jo.getInt("id"), jo.getString("nombre"), jo.getString("apellidos"), jo.getString("institucion")));
                                }

                                //Actualizar Ponentes en BD
                                updatePonentes(ponentes);

                                callback.onSuccessLista(ponentes);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error
                            Log.i("PONENTES_ERR_WS", error.toString());
                            Toast.makeText(context, "No se pueden actualizar los datos", Toast.LENGTH_SHORT).show();
                        }
                    });

                    WSSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
                } else {
                    Log.i("PONENTES_WS", "Se recuperan de la BD");
                    //Se recuperan los ponentes de la BD local
                    callback.onSuccessLista(ponentes);
                }

                return null;
            }
        }.execute();
    }

    /**
     * Método que verificaría si se debe actualizar los ponentes en la BD
     * debido a que hay cambios en el webservice
     * 
     * @// TODO: 05/12/18 Definr las reglas de como determinar si se debe actualizar los registros de ponentes 
     * 
     * @return
     */
    public static boolean hayNuevosPonentes() {
        boolean actualizar = false;

        //Se consultan los ponentes en la BD
        ponentes = (ArrayList<Ponente>) appDatabase.daoPonente().fetchAllPonentes();
        Log.i("PONENTES_CHECK_ROWS_BD", ponentes.toString());

        if(ponentes == null || ponentes.size() <= 0) {
            Log.i("PONENTES_CHECK_ROWS_BD", "BD Vacia");
            //Si la BD esta vacia, se registraran en la BD
            //Si hay cambios, actualizar la BD

            actualizar = true;
        } else {
            Log.i("PONENTES_CHECK_ROWS_BD", "BD con datos");

        }

        return actualizar;
    }

    /**
     * Método para insertar o actualizar los registros de ponentes en la BD
     * Esto ocurre solamente cuando la BD esta vacia (por el momento y hasta que se defina algo
     * que determine si se tiene que actualizar los registros de ponentes)
     * 
     * @param ponentes Lista de ponentes del WService
     */
    public static void updatePonentes(final ArrayList<Ponente> ponentes) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                for(Ponente ponente : ponentes) {
                    int id = appDatabase.daoPonente().getIdPonente(ponente.getId());
                    Log.i("PONENTES_CHECK_ID", "id: " + id);

                    if(id == 0) {
                        Log.i("PONENTES_CHECK_OP", "INSERT");
                        appDatabase.daoPonente().insertPonente(ponente);
                    } else {
                        Log.i("PONENTES_CHECK_OP", "UPDATE");
                        appDatabase.daoPonente().updatePonente(ponente);
                    }
                }

                return null;
            }
        }.execute();
    }
}
