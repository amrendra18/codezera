package com.amrendra.codefiesta.utils;

/**
 * Created by Amrendra Kumar on 17/04/16.
 */
public class CustomDate {

    String dayOfWeek;
    String dateOfMonth;
    String month;
    String year;
    String time;
    String ampm;

    public CustomDate(String date) {
        String splits[] = date.split(" ");
        dayOfWeek = splits[0];
        dateOfMonth = splits[1];
        month = splits[2];
        year = splits[3];
        time = splits[4];
        ampm = splits[5];
    }


    public String getDayOfWeek() {
        return dayOfWeek + ",";
    }

    public String getDateOfMonth() {
        return dateOfMonth;
    }

    public String getDateMonthYear() {
        return dayOfWeek.toUpperCase() + ", " + dateOfMonth + " " + month + " '" + year;
    }

    public String getFullTime(){
        return getTimeAmPm()+" "+getDateMonthYear();
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public String getTime() {
        return time;
    }

    public String getTimeAmPm() {
        return time + " " + ampm;
    }

    public String getAmPm() {
        return ampm;
    }

}
