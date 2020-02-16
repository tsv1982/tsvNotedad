package com.tsv.tsvnotedad;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Util {
    public static final int IDM_OPEN = 101;
    public static final int IDM_DELETE = 102;

    public static String getDate() {
        Date currentDate = new Date();

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String dateText = dateFormat.format(currentDate);

        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String timeText = timeFormat.format(currentDate);
        return dateText + "  " + timeText;
    }
}
