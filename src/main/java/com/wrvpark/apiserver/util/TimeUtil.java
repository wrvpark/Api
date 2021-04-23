package com.wrvpark.apiserver.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Isabel Ke
 * @author Vahid Haghighat
 * Original date:2020-02-07
 *
 * Description:time utility class
 */
public class TimeUtil {
    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
    private static final ZoneId ALBERTA = ZoneId.of("MST7MDT");
    private static final ZoneId UTC = ZoneId.of("UTC");

    /**
     * get the current time
     */
    public static String getCurrentTime()
    {
        return convertDateToString(new Date());
    }

    /**
     * convert a string to a date
     * @param time that will be converted to Date
     * @return a Date format
     */
    public static Date convertStringToDate(String time)
    {
        ZonedDateTime alberta = LocalDateTime.parse(time, formatter).atZone(ALBERTA);
        ZonedDateTime utc = alberta.withZoneSameInstant(UTC);
        return new Date(utc.toEpochSecond()*1000);
    }

    public static String convertDateToString(Date date) {
        ZonedDateTime alberta = date.toInstant().atZone(UTC).withZoneSameInstant(ALBERTA);
        return formatter.format(alberta);
    }

    public static Date generateRandomFutureDate() {
        return generateRandomFutureDate(new Date().getTime());
    }

    public static Date generateRandomFutureDate(long startDate) {
        return generateRandomDate(startDate, 100);
    }

    public static Date generateRandomPastDate() {
        return generateRandomPastDate(new Date().getTime());
    }

    public static Date generateRandomPastDate(long startDate) {
        return generateRandomDate(startDate, -100);
    }

    private static Date generateRandomDate(long startDate, int period) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, period);
        long endDate = calendar.getTime().getTime();
        if (period < 0)
            return new Date(ThreadLocalRandom.current().nextLong(endDate, startDate));
        return new Date(ThreadLocalRandom.current().nextLong(startDate, endDate));
    }

    public static Date getExpiryDate(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }
}