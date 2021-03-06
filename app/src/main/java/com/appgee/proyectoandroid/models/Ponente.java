package com.appgee.proyectoandroid.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Clase modelo para la Entidad Ponente
 */

//Es serializable para poderlo meter al Bundle y poder leer sus datos
//Encripta el objeto para poderlo pasar a ivel de bytes
//Utiliza Room para manejo con la BD
//https://developer.android.com/training/data-storage/room/defining-data
//https://android.jlelse.eu/5-steps-to-implement-room-persistence-library-in-android-47b10cd47b24

@Entity(tableName = "ponente")
public class Ponente implements Serializable {

    //Atributos de tipo basico o string ya son Serializables por default
    //Si tuvieramos otro objeto o tipo no seriablizable por default debemos definir como se serializa ese objeto

    @PrimaryKey(autoGenerate = false)
    private int id;

    @ColumnInfo(name = "nombre")
    private String nombre;

    @ColumnInfo(name = "apellidos")
    private String apellidos;

    @ColumnInfo(name = "institucion")
    private String institucion;

    @ColumnInfo(name = "biodata")
    private String biodata;

    @ColumnInfo(name = "foto")
    private String foto;

    //Para seccionar el el recyclerView por orden alfabético
    @Ignore
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Ponente() {
    }

    public Ponente(int id, String nombre, String apellidos, String institucion, String foto) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.institucion = institucion;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getBiodata() {
        return biodata;
    }

    public void setBiodata(String biodata) {
        this.biodata = biodata;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ponente ponente = (Ponente) o;

        if (id != ponente.id) return false;
        if (!nombre.equals(ponente.nombre)) return false;
        if (apellidos != null ? !apellidos.equals(ponente.apellidos) : ponente.apellidos != null)
            return false;
        if (institucion != null ? !institucion.equals(ponente.institucion) : ponente.institucion != null)
            return false;
        if (biodata != null ? !biodata.equals(ponente.biodata) : ponente.biodata != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + nombre.hashCode();
        result = 31 * result + (apellidos != null ? apellidos.hashCode() : 0);
        result = 31 * result + (institucion != null ? institucion.hashCode() : 0);
        result = 31 * result + (biodata != null ? biodata.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Ponente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", institucion='" + institucion + '\'' +
                ", biodata='" + biodata + '\'' +
                '}';
    }
}