package com.appgee.proyectoandroid.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appgee.proyectoandroid.R;
import com.appgee.proyectoandroid.adapters.ProgramaAdapter;
import com.appgee.proyectoandroid.db.Interactor;
import com.appgee.proyectoandroid.models.Ponencia;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProgramaFragment extends Fragment {
    private RecyclerView rvPrograma;

    public ProgramaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_programa, container, false);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        ProgramaAdapter adapter = new ProgramaAdapter(Interactor.obtenerPonencias());

        rvPrograma = view.findViewById(R.id.rvPrograma);
        rvPrograma.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        rvPrograma.setLayoutManager(manager);
        rvPrograma.setAdapter(adapter);

        return view;
    }
}
