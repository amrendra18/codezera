package com.amrendra.codefiesta.utils;

/**
 * Created by Amrendra Kumar on 08/05/16.
 */
public class TimerUtil {
    long days;
    long hours;
    long min;
    long sec;

    public long getDays() {
        return days;
    }

    public long getHours() {
        return hours;
    }

    public long getMin() {
        return min;
    }

    public long getSec() {
        return sec;
    }

    public TimerUtil(long diff) {
        days = diff / DateUtils.SEC_IN_ONE_DAY;
        diff %= DateUtils.SEC_IN_ONE_DAY;

        hours = diff / DateUtils.SEC_IN_ONE_HOUR;
        diff %= DateUtils.SEC_IN_ONE_HOUR;

        min = diff / DateUtils.SEC_IN_ONE_MINUTE;
        diff %= DateUtils.SEC_IN_ONE_MINUTE;

        sec = diff;
    }
}
