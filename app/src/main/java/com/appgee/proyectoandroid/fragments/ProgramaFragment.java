package com.appgee.proyectoandroid.fragments;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.appgee.proyectoandroid.R;
import com.appgee.proyectoandroid.adapters.ProgramaAdapter;
import com.appgee.proyectoandroid.db.Interactor;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProgramaFragment extends Fragment {
    private RecyclerView rvPrograma;
    private SearchView searchView;
    private SearchView.OnQueryTextListener onQueryTextListener;
    private ProgramaAdapter adapter;

    public ProgramaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Indicar a la actividad contenedora que este fragmento tiene opciones del menú
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_programa, container, false);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        adapter = new ProgramaAdapter(Interactor.obtenerPonencias(getContext()));

        rvPrograma = view.findViewById(R.id.rvPrograma);
        rvPrograma.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        rvPrograma.setLayoutManager(manager);
        rvPrograma.setAdapter(adapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.item_buscar);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();

            if (searchView != null) {
                SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
                SearchableInfo searchableInfo = searchManager.getSearchableInfo(getActivity().getComponentName());
                searchView.setSearchableInfo(searchableInfo);

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        adapter.filtrar(s);

                        return true;
                    }
                });
            }
        }

        super.onCreateOptionsMenu(menu, inflater);
    }
}
