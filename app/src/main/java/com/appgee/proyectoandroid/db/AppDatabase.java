package com.appgee.proyectoandroid.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.appgee.proyectoandroid.daos.DaoPonencia;
import com.appgee.proyectoandroid.daos.DaoPonente;
import com.appgee.proyectoandroid.models.Ponencia;
import com.appgee.proyectoandroid.models.Ponente;

@Database(entities = {Ponente.class,Ponencia.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DaoPonente daoPonente();
    public abstract DaoPonencia daoPonencia();
}
