package com.amrendra.codefiesta.utils;

import com.amrendra.codefiesta.CodeFiestaApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Amrendra Kumar on 09/04/16.
 */
public class DateUtils {

    public static long getEpochTime(String time) {
        long epochTime = -1;
        //"2013-12-08T09:00:00"
        if (null != time) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone("GMT"));
            try {
                Date date = df.parse(time);
                epochTime = date.getTime() / 1000;
            } catch (ParseException e) {
                CodeFiestaApplication.shouldNotHappen("Exception in converting time to epoch : " + "time=" + time + " exception=" + e.getLocalizedMessage());
            }
        } else {
            CodeFiestaApplication.shouldNotHappen("Time to convert to epoch is null");
        }
        return epochTime;
    }

    public static String epochToDateTimeGmt(long time) {
        long timems = time * 1000L;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        return formatter.format(new Date(timems));
    }

    public static String epochToDateTimeLocal(long time) {
        long timems = time * 1000L;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setTimeZone(TimeZone.getDefault());
        return formatter.format(new Date(timems));
    }
}
