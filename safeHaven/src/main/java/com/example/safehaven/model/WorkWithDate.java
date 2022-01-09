package com.example.safehaven.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WorkWithDate {

    public String NextDay(Integer days) throws ParseException {
        String nextDt = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(nextDt));
        c.add(Calendar.DATE, days);  // number of days to add
        nextDt = sdf.format(c.getTime());  // dt is now the new date
        return nextDt;
    }

    public String Today() throws ParseException {
        String today = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(today));
        today = sdf.format(c.getTime());  // dt is now the new date
        return today;
    }
}