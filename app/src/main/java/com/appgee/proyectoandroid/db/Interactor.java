package com.appgee.proyectoandroid.db;

import com.appgee.proyectoandroid.models.Ponencia;
import com.appgee.proyectoandroid.models.Ponente;

import java.util.ArrayList;

public class Interactor {
    public static ArrayList<Ponencia> obtenerPonencias() {
        ArrayList<Ponencia> ponencias = new ArrayList<Ponencia>();
        Ponencia ponencia;

        ponencia  = new Ponencia("Miércoles 1 de agosto de 2018", "16:00", "Antropónimos en la cultura shipibo-conibo");
        ponencia.setNombrePonente("Karen Napan Gómez");
        ponencia.setLugar("Salón 102A");
        ponencia.setModalidad("Ponencia");
        ponencias.add(ponencia);

        ponencia = new Ponencia("Jueves 2 de agosto de 2018", "10:00", "Educaplay para la enseñanza de lenguas");
        ponencia.setNombrePonente("Sonia Cruz Techica");
        ponencia.setLugar("Laboratorio 3");
        ponencia.setModalidad("Taller");
        ponencias.add(ponencia);

        ponencia = new Ponencia("Viernes 3 de agosto de 2018", "12:35", "Storytelling como estrategia didáctica en la enseñanza de idiomas");
        ponencia.setNombrePonente("Eduardo Luis Altamirano Chávez");
        ponencia.setLugar("Salón 106A");
        ponencia.setModalidad("Ponencia");
        ponencias.add(ponencia);

        return ponencias;
    }

    public static ArrayList<Ponente> obtenerPonentes() {
        ArrayList<Ponente> ponentes = new ArrayList<>();

        ponentes.add(new Ponente(1,"Juan Solis 1","Benemerito", "Biología"));
        ponentes.add(new Ponente(2,"Pedro","Benemerito", "Estéticas"));
        ponentes.add(new Ponente(3,"Emmanuel","Benemerito", "Historía"));
        ponentes.add(new Ponente(4,"Allan","Benemerito", "Física"));
        ponentes.add(new Ponente(5,"Esthela","Benemerito", "Geografía"));
        ponentes.add(new Ponente(6,"Miriam","Benemerito", "Economía"));
        ponentes.add(new Ponente(7,"Carlos","Benemerito", "Salud"));
        ponentes.add(new Ponente(8,"Juan","Benemerito", "Contaduría"));
        ponentes.add(new Ponente(9,"Juan Solis 9","Benemerito", "Administración"));
        ponentes.add(new Ponente(10,"Juan Solis 10","Benemerito", "Infrmática"));
        ponentes.add(new Ponente(11,"Juan Solis 11","Benemerito", "Políticas"));
        ponentes.add(new Ponente(12,"Juan Solis 12","Benemerito", "Fresas"));

        return ponentes;
    }
}
