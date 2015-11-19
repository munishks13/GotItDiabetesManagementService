package com.capstone.coursera.gidma.service.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

public class Utils {

    public static final String DATE_FORMAT_1 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String DATE_FORMAT_2 = "yyyy-MM-dd";

    public static final SimpleDateFormat SDF_1 = new SimpleDateFormat(DATE_FORMAT_1);
    public static final SimpleDateFormat SDF_2 = new SimpleDateFormat(DATE_FORMAT_2);

    /**
     * @param dateStr
     * @param dateFormat
     * @return Date
     */
    public static Date getDate(String dateStr, String dateFormat) {
        Date date = null;
        if (StringUtils.isNotEmpty(dateStr) && StringUtils.isNotEmpty(dateFormat)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
                date = new DateTime(sdf.parse(dateStr).getTime()).toDate();
            } catch (Exception eX) {
                eX.printStackTrace();
            }
        }
        return date;
    }

    /**
     * @param date
     * @return Date
     */
    public static Date getDateTimeLastInDay(Date date) {
        return updateTime(date, 23, 59, 59);
    }

    /**
     * @param date
     * @param hrs
     * @param min
     * @param sec
     * @return Date
     */
    public static Date updateTime(Date date, int hrs, int min, int sec) {

        if (date != null) {
            return new DateTime(date.getTime()).plusHours(hrs).plusMinutes(min).plusSeconds(sec).toDate();
        }

        return date;
    }

    public static void displayExecutionStartInfo(String info) {
        System.out.println("\n\n\n\n\n \t ===========>>>>> Executing START: " + info + " =========>>>> \n");
    }

    public static void displayExecutionInfo(String info) {
        System.out.println(info);
    }

    public static void main(String[] args) {
        Date dt = getDate("2015-09-22", DATE_FORMAT_2);
        System.out.println(dt);
        System.out.println(getDateTimeLastInDay(dt));
        System.out.println(getDate(null, DATE_FORMAT_2));
    }

}
