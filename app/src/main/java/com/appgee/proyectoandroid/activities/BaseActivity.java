package com.appgee.proyectoandroid.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ScrollView;

import com.appgee.proyectoandroid.R;

abstract public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LayoutInflater inflater = LayoutInflater.from(this);

        ScrollView scrollView = findViewById(R.id.mainScrollView);
        View childLayout = inflater.inflate(getContentAreaLayout(), scrollView, false);
        scrollView.addView(childLayout);
    }

    // Debe devolver el ID del layout que se inserta en el scrollview
    protected abstract int getContentAreaLayout();
}
