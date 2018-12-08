package com.appgee.proyectoandroid.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.appgee.proyectoandroid.models.Ponencia;

import java.util.List;

@Dao
public interface DaoPonencia {
    @Insert
    Long guardar(Ponencia ponencia);

    // Obtener todas las ponencias
    @Query("SELECT * FROM ponencia")
    List<Ponencia> buscaTodas();

    // Obtener una ponencia
    @Query("SELECT * FROM ponencia WHERE id =:ponenciaId")
    LiveData<Ponencia> busca(int ponenciaId);

    // Actualizar ponencia
    @Update
    void actualizar(Ponencia ponencia);
}
