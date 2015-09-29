package com.veaer.gank.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Veaer on 15/8/18.
 */
public class DateUtil {

    public static String getToday() {
        return toDate(new Date());
    }

    public static String toDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(date);
    }

    public static String toDate(Date date, int add) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, add);
        return toDate(calendar.getTime());
    }

    public static Date getLastdayDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    public static Date getNextdayDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

}
