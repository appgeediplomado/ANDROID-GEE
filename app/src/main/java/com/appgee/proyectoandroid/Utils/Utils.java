package com.appgee.proyectoandroid.Utils;

import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Utils {
    public static void agendarEvento(Context context, String titulo, String lugar, String fecha, String hora) {
        Calendar calendar
                = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd 'de' MMMM 'de' yyyy HH:mm", new Locale("es", "MX"));
        try {
            calendar.setTime(sdf.parse(fecha + " " + hora));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(Intent.ACTION_INSERT);

        intent.setData(CalendarContract.Events.CONTENT_URI);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra(CalendarContract.Events.TITLE, "CILLT: " + titulo);
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, lugar);
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calendar.getTimeInMillis());
        context.startActivity(intent);
    }
}
