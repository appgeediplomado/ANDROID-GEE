package com.appgee.proyectoandroid.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "ponencia")
public class Ponencia implements Serializable {
    @PrimaryKey(autoGenerate = false)
    private int id;
    private String titulo;
    private String sinopsis;
    private String modalidad;
    private String nombrePonente;
    private String fecha;
    private String hora;
    private String lugar;
    private Boolean agendada = false;
    private int calidadPonencia;
    private int experienciaPonente;
    private int relevanciaPonencia;

    public Ponencia() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getNombrePonente() {
        return nombrePonente;
    }

    public void setNombrePonente(String nombrePonente) {
        this.nombrePonente = nombrePonente;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Boolean getAgendada() {
        return agendada;
    }

    public void setAgendada(Boolean agendada) {
        this.agendada = agendada;
    }

    public int getCalidadPonencia() {
        return calidadPonencia;
    }

    public void setCalidadPonencia(int calidadPonencia) {
        this.calidadPonencia = calidadPonencia;
    }

    public int getExperienciaPonente() {
        return experienciaPonente;
    }

    public void setExperienciaPonente(int experienciaPonente) {
        this.experienciaPonente = experienciaPonente;
    }

    public int getRelevanciaPonencia() {
        return relevanciaPonencia;
    }

    public void setRelevanciaPonencia(int relevanciaPonencia) {
        this.relevanciaPonencia = relevanciaPonencia;
    }
}
