package com.veaer.gank.model;

/**
 * Created by Veaer on 15/8/19.
 */
public class VDate {
    public String TIME;
    public String YEAR;
    public String MONTH;
    public String DAY;

    public VDate(String published) {
        String[] times = published.split("-");
        this.YEAR = times[0];
        this.MONTH = getMonth(times[1]);
        this.DAY = times[2].substring(0, 2);
        this.TIME = published.split("T")[0].replace("-", "/");
    }

    public String getMonth(String monthNumber) {
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return months[Integer.parseInt(monthNumber) - 1];
    }
}
