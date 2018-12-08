package com.appgee.proyectoandroid.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.appgee.proyectoandroid.models.Ponente;

import java.util.List;

@Dao
public interface DaoPonente {
    @Insert
    Long insertPonente(Ponente ponente);


    @Query("SELECT * FROM ponente")
    List<Ponente> fetchAllPonentes();

    @Query("SELECT * FROM Ponente WHERE id =:ponenteId")
    List<Ponente> getPonente(int ponenteId);

    @Query("SELECT id FROM Ponente WHERE id =:ponenteId")
    int getIdPonente(int ponenteId);

    @Update
    void updatePonente(Ponente ponente);

    @Query("UPDATE ponente SET biodata = :biodata WHERE id =:ponenteId")
    int updatePonenteById(int ponenteId, String biodata);

    @Delete
    void deletePonente(Ponente ponente);
}
