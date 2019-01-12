package com.appgee.proyectoandroid.Utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.appgee.proyectoandroid.R;
import com.appgee.proyectoandroid.activities.PonenciaEvaluacionActivity;
import com.appgee.proyectoandroid.models.Ponencia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Utils {
    public static void agendarPonencia(Ponencia ponencia, Context context) {
        if (ponencia.getAgendada()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert);
            builder.setTitle("Error")
                    .setMessage("Esta ponencia ya está agendada en este dispositivo")
                    .setCancelable(false)
                    .setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            String fechaHora = ponencia.getFecha() + " " +  ponencia.getHora();
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd 'de' MMMM 'de' yyyy HH:mm", new Locale("es", "MX"));

            try {
                calendar.setTime(sdf.parse(fechaHora));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(Intent.ACTION_INSERT);

            intent.setData(CalendarContract.Events.CONTENT_URI);
            intent.setType("vnd.android.cursor.item/event");
            intent.putExtra(CalendarContract.Events.TITLE, "CILLT: " + ponencia.getTitulo());
            intent.putExtra(CalendarContract.Events.EVENT_LOCATION, ponencia.getLugar());
            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calendar.getTimeInMillis());
            context.startActivity(intent);

            ponencia.setAgendada(true);
        }
    }
}
