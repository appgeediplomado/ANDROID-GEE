package com.appgee.proyectoandroid.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.appgee.proyectoandroid.R;
import com.appgee.proyectoandroid.Session.SessionGee;
import com.appgee.proyectoandroid.fragments.AsistenciaFragment;
import com.appgee.proyectoandroid.fragments.AsistenciaFragmentSinSesion;
import com.appgee.proyectoandroid.fragments.InicioFragment;
import com.appgee.proyectoandroid.fragments.MapaFragment;
import com.appgee.proyectoandroid.fragments.PonentesFragment;
import com.appgee.proyectoandroid.fragments.ProgramaFragment;


public class MainActivity extends AppCompatActivity {
    private static final String BACK_STACK_PRIMER_NIVEL = "primer_nivel";
    private static final String BACK_STACK_RAIZ = "raíz";

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private SessionGee sesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sesion = new SessionGee(this);

        // Establecer nuestra ToolBar como ActionBar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //Pone el menu hamburguesa

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navview);

        // Opciones del menú del navigation view
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                boolean esFragment = false;

                Integer menuItemId = menuItem.getItemId();

                fragment = seleccionaFragment(menuItemId);
                esFragment = (fragment != null);

                if (esFragment) {
                    reemplazarFragment(fragment, menuItem.getTitle().toString(), BACK_STACK_PRIMER_NIVEL);
                } else if (menuItemId == R.id.item_acerca_de) {
                    Intent  intent = new Intent(MainActivity.this, AcercaDeActivity.class);
                    startActivity(intent);
                }

                drawerLayout.closeDrawers();
                return true;
            }
        });


        MenuItem menuItem;
        if (sesion.usuarioTieneSesionActiva()){
            menuItem = navigationView.getMenu().getItem(1);
            reemplazarFragment(new ProgramaFragment(), menuItem.getTitle().toString(), BACK_STACK_RAIZ);
        }
        else{
            menuItem = navigationView.getMenu().getItem(0);
            reemplazarFragment(new InicioFragment(), menuItem.getTitle().toString(), BACK_STACK_RAIZ);
        }
        menuItem.setChecked(true);

    }

    private Fragment seleccionaFragment(Integer menuItemId) {
        Fragment fragment = null;

        switch (menuItemId) {
            case R.id.item_inicio:
                if (sesion.usuarioTieneSesionActiva())
                    fragment = new ProgramaFragment();
                else
                    fragment = new InicioFragment();

                break;

            case R.id.item_programa:
                fragment = new ProgramaFragment();

                break;

            case R.id.item_ponentes:
                fragment = new PonentesFragment();

                break;

            case R.id.item_asistencia:
                if (sesion.usuarioTieneSesionActiva())
                    fragment = new AsistenciaFragment();
                else
                    fragment = new AsistenciaFragmentSinSesion();
                break;

            case R.id.item_mapa:
                fragment = new MapaFragment();

                break;
        }

        return fragment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // home corresponde al menú "hamburguesa"
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void reemplazarFragment(Fragment fragment, String titulo, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        manager.popBackStack(BACK_STACK_PRIMER_NIVEL, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        transaction.replace(R.id.content_frame, fragment);

        if (sesion.usuarioTieneSesionActiva()) {
            if (!(fragment instanceof ProgramaFragment)) {
                transaction.addToBackStack(BACK_STACK_PRIMER_NIVEL);
            }
        } else {
            if (!(fragment instanceof InicioFragment)) {
                transaction.addToBackStack(BACK_STACK_PRIMER_NIVEL);
            }
        }

        transaction.commit();
        getSupportActionBar().setTitle(titulo);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
        } else {
            if (sesion.usuarioTieneSesionActiva()) {
                getSupportActionBar().setTitle("Programa");
            } else {
                getSupportActionBar().setTitle("Inicio");
            }

            navigationView.getMenu().getItem(0).setChecked(true);

            super.onBackPressed();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        MenuItem menuItem = navigationView.getCheckedItem();

        if (menuItem != null) {
            Fragment fragment = seleccionaFragment(menuItem.getItemId());

            if (fragment != null) {
                reemplazarFragment(fragment, menuItem.getTitle().toString(), BACK_STACK_RAIZ);
            }
        }
    }
}
