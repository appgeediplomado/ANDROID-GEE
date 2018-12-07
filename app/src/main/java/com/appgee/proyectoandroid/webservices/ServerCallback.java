package com.appgee.proyectoandroid.webservices;

import com.appgee.proyectoandroid.models.Ponente;

import java.util.ArrayList;

public interface ServerCallback<T> {
    void onSuccessLista(ArrayList<T> lista);
}
