package com.capstone.coursera.gidma.service.integration.test;

import static com.capstone.coursera.gidma.service.utils.Utils.SDF_1;
import static com.capstone.coursera.gidma.service.utils.Utils.SDF_2;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;

public class TestWithMain {

    public static void main(String[] args) {

        // Date dt1 = new Date("2015/10/10");
        // Date dt2 = new Date("2015/11/05");

        Date dt1 = new DateTime(2015, 10, 10, 0, 0, 0).toDate();
        Date dt2 = new DateTime(2015, 11, 5, 0, 0, 0).toDate();

        System.out.println("dt1 : " + dt1);
        System.out.println("dt2 : " + dt2);

        long daysDiff = TimeUnit.DAYS.convert((dt2.getTime() - dt1.getTime()), TimeUnit.MILLISECONDS);

        System.out.println("daysDiff : " + daysDiff);

        long totalTimes = 3;

        DateTime dateTimeStart = new DateTime(dt1.getTime());

        for (int i = 1; i <= daysDiff; i++) {
            System.out.println("\n\n");
            for (int j = 0; j < totalTimes; j++) {
                Date currentDate = null;
                if (j == 0) {
                    currentDate = dateTimeStart.withHourOfDay(8).toDate();
                } else if (j == 1) {
                    currentDate = dateTimeStart.withHourOfDay(14).toDate();
                }
                if (j == 2) {
                    currentDate = dateTimeStart.withHourOfDay(20).toDate();
                }

                try {
                    currentDate = SDF_1.parse(SDF_1.format(currentDate));
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                System.out.println("==> " + currentDate);

            }
            dateTimeStart = dateTimeStart.plusDays(1);
        }

        int min = 5;
        int max = 10;

        for (int i = 0; i < 10; i++) {
            System.out.println(randomInteger(min, max));
            // System.out.println(randomdDouble(min, max));
        }

        String dateStr = "2015-10-09";

        try {
            System.out.println("\n\n");
            System.out.println(SDF_2.parseObject(dateStr));
            System.out.println(SDF_2.format(SDF_2.parseObject(dateStr)));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // DateTimeFormat dtf =

    }

    public static int randomInteger(int min, int max) {
        // Random rand = new Random();
        // int randomNum = min + (int)(Math.random() * ((max - min) + 1))
        // return randomNum;

        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public static double randomdDouble(int min, int max) {
        // Random rand = new Random();
        // int randomNum = min + (int)(Math.random() * ((max - min) + 1))
        // return randomNum;

        return ThreadLocalRandom.current().nextDouble(min, max);
    }

}
