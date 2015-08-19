package com.veaer.gank.model;

/**
 * Created by Veaer on 15/8/19.
 */
public class VDate {
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
