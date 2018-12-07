package com.appgee.proyectoandroid.fragments;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import com.appgee.proyectoandroid.R;
import com.appgee.proyectoandroid.Session.SessionGee;


import java.util.HashMap;
import java.util.regex.Pattern;

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
                if(camposValidos())
                {
                    SessionGee sesionGee = new SessionGee(getContext());

                    HashMap<String, String> datosSesion = new HashMap<String, String>();
                    datosSesion.put(sesionGee.KEY_ID, "1");
                    datosSesion.put(sesionGee.KEY_NOMBRE, "Nombre y Apellidos");
                    datosSesion.put(sesionGee.KEY_CORREO, "correo@dominio.com");

                    sesionGee.crearSesion(datosSesion);


                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_frame, new ProgramaFragment());
                    transaction.commit();

                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Programa");

                    MenuItem menuItem = navigationView.getMenu().getItem(1);
                    menuItem.setChecked(true);

                }
            }
        });
    }


    public boolean camposValidos(){

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



}
