package com.veaer.gank.util;

/**
 * Created by Veaer on 15/8/18.
 */
public class DateUtil {

    public static VDate publish2date(String published) {
        return new VDate(published);
    }

    public static class VDate {
        public String Year;
        public String Month;
        public String Day;
        public VDate(String published) {
            String[] times = published.split("-");
            this.Year = times[0];
            this.Month = getMonth(times[1]);
            this.Day = times[2].substring(0, 2);
        }

        public String getMonth(String monthNumber) {
            String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
            return months[Integer.parseInt(monthNumber) - 1];
        }
    }
}
