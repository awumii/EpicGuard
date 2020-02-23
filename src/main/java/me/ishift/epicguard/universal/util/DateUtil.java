package me.ishift.epicguard.universal.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {
    public static String getTime() {
        return format("HH:mm:ss");
    }

    public static String getDate() {
        return format("dd-M-yyyy");
    }

    public static String format(String sdfValue) {
        final Calendar cal = Calendar.getInstance();
        final SimpleDateFormat sdf = new SimpleDateFormat(sdfValue);
        return sdf.format(cal.getTime());
    }
}
