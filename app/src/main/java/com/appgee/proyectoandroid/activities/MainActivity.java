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
import com.appgee.proyectoandroid.fragments.AsistenciaFragment;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                boolean esFragment = false;
                Fragment fragment = null;

                switch (menuItem.getItemId()) {
                    case R.id.item_inicio:
                        fragment = new InicioFragment();
                        esFragment = true;

                        break;

                    case R.id.item_programa:
                        fragment = new ProgramaFragment();
                        esFragment = true;

                        break;

                    case R.id.item_ponentes:
                        fragment = new PonentesFragment();
                        esFragment = true;

                        break;

                    case R.id.item_asistencia:
                        fragment = new AsistenciaFragment();
                        esFragment = true;

                        break;

                    case R.id.item_mapa:
                        fragment = new MapaFragment();
                        esFragment = true;

                        break;

                    case R.id.item_acerca_de:
                        Intent  intent = new Intent(MainActivity.this, AcercaDeActivity.class);
                        startActivity(intent);

                        break;
                }

                if (esFragment) {
                    reemplazarFragment(fragment, menuItem, BACK_STACK_PRIMER_NIVEL);
                }

                drawerLayout.closeDrawers();
                return true;
            }
        });

        reemplazarFragment(new InicioFragment(), navigationView.getMenu().getItem(0), BACK_STACK_RAIZ);
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

    private void reemplazarFragment(Fragment fragment, MenuItem menuItem, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        manager.popBackStack(BACK_STACK_PRIMER_NIVEL, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        transaction.replace(R.id.content_frame, fragment);

        if (!(fragment instanceof InicioFragment)) {
            transaction.addToBackStack(BACK_STACK_PRIMER_NIVEL);
        }

        transaction.commit();

        getSupportActionBar().setTitle(menuItem.getTitle());
    }
}
