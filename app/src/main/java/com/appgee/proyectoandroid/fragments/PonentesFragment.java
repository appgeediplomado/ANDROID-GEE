package com.appgee.proyectoandroid.fragments;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.appgee.proyectoandroid.activities.PonenteDetallesActivity;
import com.appgee.proyectoandroid.adapters.PonenteAdapter;
import com.appgee.proyectoandroid.db.Interactor;
import com.appgee.proyectoandroid.listeners.OnPonenteClickListener;
import com.appgee.proyectoandroid.models.Ponente;
import com.appgee.proyectoandroid.webservices.ServerCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 */
public class PonentesFragment extends Fragment implements OnPonenteClickListener {
    private RecyclerView ponentesRecyclerView;

    //Seteamos el adaptador como atributo
    private PonenteAdapter ponenteAdapter;
    private SearchView searchView;
    private SearchView.OnQueryTextListener queryTextListener;

    ArrayList<Ponente> ponentes = new ArrayList<>();

    public PonentesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PonentesFragment newInstance() {
        PonentesFragment fragment = new PonentesFragment();
        return fragment;
    }

    //Cuando se crea la instancia
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Interactor.crearBD(getContext());
        setHasOptionsMenu(true);
    }

    //Cuando se crea en la vista, asociamos el XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ponentes, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //creamos referencia a la vista
        ponentesRecyclerView = view.findViewById(R.id.rvPonentes);

        //Lo manejamos en forma de lista
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        ponentesRecyclerView.setHasFixedSize(true);
        ponentesRecyclerView.setLayoutManager(manager);

        //Incluye una linea divisora entre cada renglon de la lista
        ponentesRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        //https://stackoverflow.com/questions/23833977/android-wait-for-volley-response-to-return
        //Factory method design pattern solution
        //Se ejecuta asyncronramente la recuperación de los ponentes
        Interactor.obtenerPonentes(getContext(), new ServerCallback<Ponente>() {
            @Override
            public void onSuccessLista(ArrayList<Ponente> lista) {
                //Log.i("PONENTES_AFTER", "Callback de ponentes");

                //Recibimos la lista de ponentes
                ponentes = new ArrayList<Ponente>(lista);

                Log.i("PONENTES_CHECK_LISTA", ponentes.toString());

                //La lista se debe insertar en la vista desde el hilo de la UI principal
                //https://stackoverflow.com/questions/3875184/cant-create-handler-inside-thread-that-has-not-called-looper-prepare
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        //Lista seccionada alfabeticamente y buscable | Basado en:
                        //https://stackoverflow.com/questions/34142289/display-namelist-in-recyclerview-under-each-letter-in-alphabetic-order-android
                        //https://stackoverflow.com/questions/40683817/how-to-set-two-adapters-to-one-recyclerview
                        ponenteAdapter = new PonenteAdapter(addAlphabets(sortList(ponentes)));
                        ponenteAdapter.setOnPonenteClickListener(PonentesFragment.this);
                        ponentesRecyclerView.setAdapter(ponenteAdapter);
                        ponenteAdapter.notifyDataSetChanged();
                    }
                });

            }
        }, Interactor.NO_ID);

        //Log.i("PONENTES_BEFORE", "Antes de que acabe obtenerPonentes");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.item_buscar);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }

        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("GEE-SEARCH", "onQueryTextChange: " + newText);
                    //Filtra al ir escribiendo
                    ponenteAdapter.getFilter().filter(newText);
                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("GEE-SEARCH", "onQueryTextSubmit: " + query);

                    //Filtra al dar ENTER
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_buscar:
                // Not implemented here
                return false;
            default:
                break;
        }

        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPonenteClick(Ponente ponente) {
        Intent intent = new Intent(getActivity(), PonenteDetallesActivity.class);
        intent.putExtra("ponente", ponente);
        startActivity(intent);
    }

    ArrayList<Ponente> sortList(ArrayList<Ponente> list) {
        Collections.sort(list, new Comparator<Ponente>() {
            @Override
            public int compare(Ponente teamMember1, Ponente teamMember2) {
                return teamMember1.getNombre().compareTo(teamMember2.getNombre());
            }
        });
        return list;
    }

    ArrayList<Ponente> addAlphabets(ArrayList<Ponente> list) {
        int i = 0;
        ArrayList<Ponente> customList = new ArrayList<Ponente>();

        if (!list.isEmpty()) {
            Ponente firstMember = new Ponente();
            firstMember.setNombre(String.valueOf(list.get(0).getNombre().charAt(0)));
            firstMember.setType(1);
            customList.add(firstMember);
            for (i = 0; i < list.size() - 1; i++) {
                Ponente teamMember = new Ponente();
                char name1 = list.get(i).getNombre().charAt(0);
                char name2 = list.get(i + 1).getNombre().charAt(0);
                if (name1 == name2) {
                    list.get(i).setType(2);
                    customList.add(list.get(i));
                } else {
                    list.get(i).setType(2);
                    customList.add(list.get(i));
                    teamMember.setNombre(String.valueOf(name2));
                    teamMember.setType(1);
                    customList.add(teamMember);
                }
            }
            list.get(i).setType(2);
            customList.add(list.get(i));
        }
        return customList;
    }
}
