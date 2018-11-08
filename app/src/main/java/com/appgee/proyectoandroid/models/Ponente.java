package com.appgee.proyectoandroid.models;

import java.io.Serializable;

/**
 * Clase modelo para la Entidad Ponente
 */

//Es serializable para poderlo meter al Bundle y poder leer sus datos
//Encripta el obkjeto para poderlo pasar a ivel de bytes

public class Ponente implements Serializable {

    //Atributos de tipo basico o string ya son Serializables por default
    //Si tuvieramos otro objeto o tipo no seriablizable por default debemos definir como se serializa ese objeto
    private int id;
    private String nombre;
    private String apellidos;
    private String institucion;

    //Para seccionar el el recyclerView por orden alfabético
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Ponente() {
    }

    public Ponente(int id, String nombre, String apellidos, String institucion) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.institucion = institucion;
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
        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + nombre.hashCode();
        result = 31 * result + (apellidos != null ? apellidos.hashCode() : 0);
        result = 31 * result + (institucion != null ? institucion.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Ponente{" +
                "id=" + id +
                ", name='" + nombre + '\'' +
                ", school='" + apellidos + '\'' +
                ", address='" + institucion + '\'' +
                '}';
    }
}