package com.appgee.proyectoandroid.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.appgee.proyectoandroid.R;
import com.appgee.proyectoandroid.activities.DetallesAsistenciaActivity;
import com.appgee.proyectoandroid.activities.UbicacionActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapaFragment extends Fragment implements View.OnClickListener {

    TextView mostrarPlantaBajaA;
    TextView mostrarPrimerPisoA;
    TextView mostrarTercerPisoA;

    TextView mostrarBasamentoB;
    TextView mostrarPlantaBajaB;
    TextView mostrarPrimerPisoB;

    public MapaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_mapa, container, false);
        mostrarPlantaBajaA = v.findViewById(R.id.tvPlantaBajaA);
        mostrarPrimerPisoA = v.findViewById(R.id.tvPrimerPisoA);
        mostrarTercerPisoA = v.findViewById(R.id.tvTercerPisoA);

        mostrarBasamentoB = v.findViewById(R.id.tvBasamentoB);
        mostrarPlantaBajaB = v.findViewById(R.id.tvPlantaBajaB);
        mostrarPrimerPisoB = v.findViewById(R.id.tvPrimerPisoB);

        mostrarPlantaBajaA.setOnClickListener(this);
        mostrarPrimerPisoA.setOnClickListener(this);
        mostrarTercerPisoA.setOnClickListener(this);

        mostrarBasamentoB.setOnClickListener(this);
        mostrarPlantaBajaB.setOnClickListener(this);
        mostrarPrimerPisoB.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        String edificio = null;
        String ubicacion = null;

        switch (v.getId()){
            case R.id.tvPlantaBajaA:
                edificio = "Edificio A";
                ubicacion = mostrarPlantaBajaA.getText().toString();
                break;
            case R.id.tvPrimerPisoA:
                edificio = "Edificio A";
                ubicacion = mostrarPrimerPisoA.getText().toString();
                break;
            case R.id.tvTercerPisoA:
                edificio = "Edificio A";
                ubicacion = mostrarTercerPisoA.getText().toString();
                break;
            case R.id.tvBasamentoB:
                edificio = "Edificio B";
                ubicacion = mostrarBasamentoB.getText().toString();
                break;
            case R.id.tvPlantaBajaB:
                edificio = "Edificio B";
                ubicacion = mostrarPlantaBajaB.getText().toString();
                break;
            case R.id.tvPrimerPisoB:
                edificio = "Edificio B";
                ubicacion = mostrarPrimerPisoB.getText().toString();
                break;
                default: ubicacion = "Sin ubicación";
        }

        Intent mostrarUbicacion = new Intent(getContext(), UbicacionActivity.class);
        mostrarUbicacion.putExtra("Edificio",edificio);
        mostrarUbicacion.putExtra("TituloUbicacion",ubicacion);
        getActivity().startActivity(mostrarUbicacion);
    }
}
