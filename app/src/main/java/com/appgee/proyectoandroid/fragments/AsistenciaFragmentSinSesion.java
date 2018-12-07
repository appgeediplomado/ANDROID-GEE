package com.appgee.proyectoandroid.fragments;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.appgee.proyectoandroid.R;



/**
 * A simple {@link Fragment} subclass.
 */
public class AsistenciaFragmentSinSesion extends Fragment {

    Button btnIrInicioSesion;
    private NavigationView navigationView;


    public AsistenciaFragmentSinSesion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_asistencia_sin_sesion, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        navigationView = ((AppCompatActivity) getActivity()).findViewById(R.id.navview);
        btnIrInicioSesion = getView().findViewById(R.id.btnMostrarInicioSesion);


        btnIrInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, new InicioFragment());
                transaction.commit();

                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Inicio");

                MenuItem menuItem = navigationView.getMenu().getItem(0);
                menuItem.setChecked(true);

            }
        });
    }


}
