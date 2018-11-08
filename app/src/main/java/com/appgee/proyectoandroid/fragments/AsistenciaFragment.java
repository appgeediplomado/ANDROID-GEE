package com.appgee.proyectoandroid.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.appgee.proyectoandroid.R;
import com.appgee.proyectoandroid.activities.DetallesAsistenciaActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class AsistenciaFragment extends Fragment implements View.OnClickListener {

    Button btnDetallesAsistencia;

    public AsistenciaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_asistencia, container, false);

        btnDetallesAsistencia = v.findViewById(R.id.btnDetallesAsistencia);
        btnDetallesAsistencia.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        //Toast.makeText(getContext(), "Algo", Toast.LENGTH_SHORT).show();
        Intent irDetallesAsistencia = new Intent(getContext(), DetallesAsistenciaActivity.class);
        getActivity().startActivity(irDetallesAsistencia);

    }
}
