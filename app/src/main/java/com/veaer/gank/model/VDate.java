package com.veaer.gank.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Veaer on 15/8/19.
 */
public class VDate implements Serializable {
    public String TIME;
    public int YEAR;
    public int MONTH;
    public int DAY;

    public VDate(Date published) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(published);
        this.YEAR = calendar.get(Calendar.YEAR);
        this.MONTH = calendar.get(Calendar.MONTH) + 1;
        this.DAY = calendar.get(Calendar.DAY_OF_MONTH);
        this.TIME = this.YEAR + "/" + this.MONTH + "/" + this.DAY;
    }

    public String getMonth() {
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return months[this.MONTH - 1];
    }
}
