package com.appgee.proyectoandroid.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.appgee.proyectoandroid.daos.DaoPonente;
import com.appgee.proyectoandroid.models.Ponente;

@Database(entities = {Ponente.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DaoPonente daoPonente();
}
