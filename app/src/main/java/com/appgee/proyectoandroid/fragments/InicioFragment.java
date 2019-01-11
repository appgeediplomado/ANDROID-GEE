package com.appgee.proyectoandroid.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.appgee.proyectoandroid.R;
import com.appgee.proyectoandroid.Session.SessionGee;
import com.appgee.proyectoandroid.Utils.Config;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.regex.Pattern;

import static android.support.v4.content.ContextCompat.getSystemService;

/**
 * A simple {@link Fragment} subclass.
 */
public class InicioFragment extends Fragment {

    private TextInputEditText txtUsuario = null;
    private TextInputEditText txtPassword = null;
    private Button btnIniciarSesion = null;

    private NavigationView navigationView;

    private static final String BACK_STACK_PRIMER_NIVEL = "primer_nivel";



    public InicioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        navigationView = ((AppCompatActivity) getActivity()).findViewById(R.id.navview);
        btnIniciarSesion = getView().findViewById(R.id.btnInicioSesion);

        txtUsuario = getView().findViewById(R.id.textInputEditText_usuario);
        txtPassword= getView().findViewById(R.id.textInputEditText_password);


        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                if(camposValidos())
                {
                    validarUsuario();
                }
            }
        });
    }


    private boolean camposValidos(){

        if(txtUsuario.getText().toString().trim().isEmpty()){
            txtUsuario.setError("Este campo es requerido.");
            return  false;
        }else{
            Pattern pattern = Patterns.EMAIL_ADDRESS;
            if(!pattern.matcher(txtUsuario.getText().toString().trim()).matches()) {
                txtUsuario.setError("Formato de correo incorrecto.");
                return false;
            }
        }

        if(txtPassword.getText().toString().trim().isEmpty()){
            txtPassword.setError("Este campo es requerido");
            return  false;
        }

        return true;
    }

    private void validarUsuario(){

        String URL = Config.WS_BASE_URL + "/asistentes/sesion/" + txtUsuario.getText().toString().trim();

        JsonObjectRequest stringRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject asistente = response.getJSONObject("asistente");

                            String password = asistente.getString("password");

                            if (txtPassword.getText().toString() != password) {

                            }

                            if(!asistente.isNull("id")){
                                SessionGee sesionGee = new SessionGee(getContext());

                                HashMap<String, String> datosSesion = new HashMap<String, String>();
                                datosSesion.put(sesionGee.KEY_ID, asistente.getString("id"));
                                datosSesion.put(sesionGee.KEY_NOMBRE, asistente.getString("nombre") + " " + asistente.getString("apellidos"));
                                datosSesion.put(sesionGee.KEY_CORREO, asistente.getString("correo"));
                                sesionGee.crearSesion(datosSesion);

                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction.replace(R.id.content_frame, new ProgramaFragment());
                                transaction.commit();

                                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Programa");

                                MenuItem menuItem = navigationView.getMenu().getItem(1);
                                menuItem.setChecked(true);
                            }

                        } catch (JSONException e) {
                            Toast.makeText(getContext(), "Error al validar credenciales", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.Theme_AppCompat_Light_Dialog_Alert);
                        builder.setTitle("Error")
                                .setMessage("Usuario no encontrado, verifique.")
                                .setCancelable(false)
                                .setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }
}
