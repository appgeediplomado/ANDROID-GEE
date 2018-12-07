package com.appgee.proyectoandroid.Session;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionGee {

    private SharedPreferences preferenciasGee;
    private SharedPreferences.Editor editor;
    private Context contexto;

    private static final String PREFERENCIAS_GEE = "PreferenciasGee";
    private static final String USUARIO_CON_SESION = "UsuarioConSesionActiva";

    public static final String KEY_ID = "idUsuario";
    public static final String KEY_NOMBRE = "nombreCompleto";
    public static final String KEY_CORREO = "correoElectronico";


    public SessionGee(Context contexto) {
        this.contexto = contexto;
        preferenciasGee = contexto.getSharedPreferences(PREFERENCIAS_GEE, contexto.MODE_PRIVATE);
        editor = preferenciasGee.edit();
    }

    public void crearSesion(HashMap<String, String> usuario){
        editor.putBoolean(USUARIO_CON_SESION, true);
        editor.putString(KEY_NOMBRE, usuario.get(KEY_NOMBRE));
        editor.putString(KEY_CORREO, usuario.get(KEY_CORREO));
        editor.putInt(KEY_ID, Integer.parseInt(usuario.get(KEY_ID)));
        editor.commit();
    }

    public void cerrarSesion(){
        editor.clear();
        editor.commit();
    }

    public HashMap<String, String> getDetallesSesion(){
        HashMap<String, String> sesion = new HashMap<String, String>();
        sesion.put(KEY_NOMBRE, preferenciasGee.getString(KEY_NOMBRE, null));
        sesion.put(KEY_CORREO, preferenciasGee.getString(KEY_CORREO, null));

        return sesion;
    }

    public boolean usuarioTieneSesionActiva(){
        return preferenciasGee.getBoolean(USUARIO_CON_SESION, false);
    }

    public int getUsuarioId(){
        return preferenciasGee.getInt(KEY_ID, 0);
    }
}
