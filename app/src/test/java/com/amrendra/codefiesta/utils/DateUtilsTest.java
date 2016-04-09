package com.amrendra.codefiesta.utils;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.TimeZone;

/**
 * Created by Amrendra Kumar on 09/04/16.
 */
public class DateUtilsTest {

    String timeStr[] = {
            "2016-04-08T18:30:00",
            "2016-04-08T08:30:00",
            "2016-04-11T09:30:00",
            "2016-04-01T09:32:00",
    };

    String timeStrGMT[] = {
            "2016-04-08 18:30:00",
            "2016-04-08 08:30:00",
            "2016-04-11 09:30:00",
            "2016-04-01 09:32:00",
    };

    String timeStrIST[] = {
            "2016-04-09 00:00:00",
            "2016-04-08 14:00:00",
            "2016-04-11 15:00:00",
            "2016-04-01 15:02:00",
    };

    long epochTimes[] = {
            1460140200,
            1460104200,
            1460367000,
            1459503120,
    };

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetEpochTime() throws Exception {
        for (int i = 0; i < timeStr.length; i++) {
            long t = DateUtils.getEpochTime(timeStr[i]);
            Assert.assertEquals("Epoch time different", epochTimes[i], t);
        }
    }

    @Test
    public void testEpochToDateTimeGMT() throws Exception {
        for (int i = 0; i < epochTimes.length; i++) {
            String t = DateUtils.epochToDateTimeGmt(epochTimes[i]);
            Assert.assertEquals("Epoch time different", timeStrGMT[i], t);
        }
    }

    @Test
    public void testEpochToDateTimeLocal() throws Exception {
        TimeZone.setDefault(TimeZone.getTimeZone("IST"));
        for (int i = 0; i < epochTimes.length; i++) {
            String t = DateUtils.epochToDateTimeLocal(epochTimes[i]);
            Assert.assertEquals("Epoch time different", timeStrIST[i], t);
        }
    }

}