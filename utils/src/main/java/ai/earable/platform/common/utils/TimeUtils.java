package ai.earable.platform.common.utils;

import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.IsoFields;
import java.time.temporal.JulianFields;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by BinhNH on 3/28/2022
 * <a href="https://www.epochconverter.com/days/2022">...</a>
 * TODO: Handle NumberFormatException when expose methods below
 */
public final class TimeUtils {

    public static final String VN_TIME_ZONE_STRING = "Asia/Ho_Chi_Minh";
    public static final String AMERICA_TZ_STRING = "America/New_York";

    public static long getCurrentTimestamp(String timezone){
        TimeZone timeZone = TimeZone.getTimeZone(timezone);
        Calendar calendar = Calendar.getInstance(timeZone);
        return calendar.getTimeInMillis();
    }

    public static long getNextTimestamp(String timezone, int plusSecond){
        TimeZone timeZone = TimeZone.getTimeZone(timezone);
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.add(Calendar.SECOND, plusSecond);
        return calendar.getTimeInMillis() / 1000L;
    }

    public static long getCurrentUnixTimestamp(String timezone){
        return getCurrentTimestamp(timezone) / 1000L;
    }

    public static ZoneOffset getZoneOffset(long epochMillis, String zoneName) {
        Instant instant = Instant.ofEpochMilli(epochMillis);
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of(zoneName));
        return zonedDateTime.getOffset();
    }

    public static int getNumberOfSecondsBetween(long timestampFirst, long timestampLast){
        return (int) (timestampLast - timestampFirst);
    }

    public static Date convertFromUnixTimestamp(long timeStamp){
        return new java.util.Date(timeStamp*1000);
    }

    public static boolean isGreaterThan(LocalTime input, LocalTime compareTo){
        return input.compareTo(compareTo) > 0;
    }

    public static boolean isEqual(LocalTime input, LocalTime compareTo){
        return input.compareTo(compareTo) == 0;
    }

    public static boolean isLessThan(LocalTime input, LocalTime compareTo){
        return input.compareTo(compareTo) < 0;
    }

    public static boolean isLessThanOrEqual(LocalTime input, LocalTime compareTo){
        return input.compareTo(compareTo) <= 0;
    }

    public static boolean isGreaterThanOrEqual(LocalTime input, LocalTime compareTo){
        return input.compareTo(compareTo) >= 0;
    }

    public static int getHourFromTimestamp(long timestamp, String timezone){
        Timestamp stamp = new Timestamp(timestamp);
        Date date = new Date(stamp.getTime());
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone(timezone));
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        return cal.get(Calendar.MINUTE) > 45 ? hour+1 : hour;
    }

    public static int getHourFromTimestamp(String timestamp, String timezone){
        return getHourFromTimestamp(convertFrom(timestamp), timezone);
    }

    public static int getDayOfYearFrom(long timestamp, String timezone){
        Date dateTime = new Date(timestamp*1000);
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(timezone));
        cal.setTime(dateTime);
        return cal.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * Using LocalDate to get week of year
     * Note: Java return wrong value if you use Calendar
     */
    public static int getWeekOfYearFrom(int year, int dayOfYear){
        LocalDate localDate = LocalDate.ofYearDay(year, dayOfYear);
        //return localDate.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
        //return localDate.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
        return localDate.get(WeekFields.SUNDAY_START.weekOfYear()); //This map to ios apple week of year from mobile
    }

    public static int getMonthOfYearFrom(long timestamp, String timezone){
        Date dateTime = new Date(timestamp*1000);
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(timezone));
        cal.setTime(dateTime);
        return cal.get(Calendar.MONTH) + 1;
    }

    public static int getYearFrom(long timestamp, String timezone){
        Date dateTime = new Date(timestamp*1000);
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(timezone));
        cal.setTime(dateTime);
        return cal.get(Calendar.YEAR);
    }

    public static boolean isSameDay(long timestamp1, long timestamp2, String timezone){
        return getDayOfYearFrom(timestamp1, timezone) == getDayOfYearFrom(timestamp2, timezone);
    }

    public static boolean isSameDay(String timestamp1, String timestamp2, String timezone){
        return isSameDay(convertFrom(timestamp1), convertFrom(timestamp2), timezone);
    }

    public static LocalDate getDateFrom(int dayOfYear, int year){
        return Year.of(year).atDay(dayOfYear);
    }

    public static long getFirstTimestampOf(int dayOfYear, int year){
        return java.sql.Timestamp.valueOf(getDateFrom(dayOfYear, year)+" 00:00:00.0").getTime()/1000L;
    }

    public static long getLastTimestampOf(int dayOfYear, int year){
        return java.sql.Timestamp.valueOf(getDateFrom(dayOfYear, year)+" 23:59:59.0").getTime()/1000L;
    }

    public static long getFirstTimestampOf(String dayOfYear, String year){
        return getFirstTimestampOf(Integer.parseInt(dayOfYear), Integer.parseInt(year));
    }

    public static long convertFrom(String timestamp){
        return Double.valueOf(timestamp).longValue();
    }

    public static boolean isLeapYear(int year, String timezone) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(timezone));
        cal.set(Calendar.YEAR, year);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
    }

    public static void main(String[] args) {
        int year = 2022;
        int day = 264;

        System.out.println(getWeekOfYearFrom(year, day));

        LocalDate localDate = LocalDate.ofYearDay(year, day);
        System.out.println(localDate.get(ChronoField.ALIGNED_WEEK_OF_YEAR));
        System.out.println(localDate.get(WeekFields.SUNDAY_START.weekOfYear()));

    }
}
