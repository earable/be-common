package ai.earable.platform.common.utils;

import org.springframework.aop.ThrowsAdvice;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.IsoFields;
import java.time.temporal.WeekFields;
import java.util.Arrays;
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

    //TODO: Must use timezone
    public static Date convertFromUnixTimestamp(long timeStamp){
        return new java.util.Date(timeStamp*1000);
    }

    public static boolean isGreaterThan(LocalDateTime input, LocalDateTime compareTo){
        return input.compareTo(compareTo) > 0;
    }

    public static boolean isEqual(LocalDateTime input, LocalDateTime compareTo){
        return input.compareTo(compareTo) == 0;
    }

    public static boolean isLessThan(LocalDateTime input, LocalDateTime compareTo){
        return input.compareTo(compareTo) < 0;
    }

    public static boolean isLessThanOrEqual(LocalDateTime input, LocalDateTime compareTo){
        return input.compareTo(compareTo) <= 0;
    }

    public static boolean isGreaterThanOrEqual(LocalDateTime input, LocalDateTime compareTo){
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
        int length = (int) (Math.log10(timestamp) + 1);
        Date dateTime = length > 10 ? new Date(timestamp) : new Date(timestamp*1000);
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(timezone));
        cal.setTime(dateTime);
        return cal.get(Calendar.DAY_OF_YEAR);
    }

    @Deprecated
    public static int getWeekOfYearFrom(long timestamp, String timezone){
        Date dateTime = new Date(timestamp*1000);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone(timezone));
        calendar.setTime(dateTime);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public static int getIso8601WeekOfYearFrom(int dayOfYear, int year){
        return LocalDate.ofYearDay(year, dayOfYear).get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
    }

    /**
     * Using this method to map to mobile at this time
     */
    public static int getWeekOfYearFrom(int dayOfYear, int year){
        LocalDate localDate = LocalDate.ofYearDay(year, dayOfYear);
        int weekOfYear = localDate.get(WeekFields.ISO.weekOfYear()) + 1;
        return weekOfYear == 53 ? 1 : weekOfYear;
    }

    public static int getMonthOfYearFrom(long timestamp, String timezone){
        Date dateTime = new Date(timestamp*1000);
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(timezone));
        cal.setTime(dateTime);
        return cal.get(Calendar.MONTH) + 1;
    }

    public static Date getDateFromDayOfYear(Calendar calendar, int dayOfYear){
        calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
        return calendar.getTime();
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

    public static boolean isSameWeek(int dayOfYear1, int year1, int dayOfYear2, int year2){
        return getIso8601WeekOfYearFrom(dayOfYear1, year1) == getIso8601WeekOfYearFrom(dayOfYear2, year2);
    }

    /**
     * To get last timestamp of
     * @param dayOfYear - day of the year
     * @param year - year
     * @param timezone - timezone id
     * @return last timestamp of this day by this timezone
     *
     * Note: You should use input from mobile to sync output
     */
    public static long getLastTimestampOf(int dayOfYear, int year, String timezone){
        LocalDate ld = LocalDate.ofYearDay(year, dayOfYear);
        LocalTime lt = LocalTime.of(23,59,59);
        LocalDateTime ldt = LocalDateTime.of(ld, lt);
        ZoneId zoneId = ZoneId.of(timezone);
        return ldt.atZone(zoneId).toEpochSecond();
    }

    public static long getFirstTimestampOf(int dayOfYear, int year, String timezone){
        LocalDate ld = LocalDate.ofYearDay(year, dayOfYear);
        LocalTime lt = LocalTime.of(0, 0, 0);
        LocalDateTime ldt = LocalDateTime.of(ld, lt);
        ZoneId zoneId = ZoneId.of(timezone);
        return ldt.atZone(zoneId).toEpochSecond();
    }

    public static long convertFrom(String timestamp){
        return Double.valueOf(timestamp).longValue();
    }

    public static boolean isLeapYear(int year, String timezone) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(timezone));
        cal.set(Calendar.YEAR, year);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
    }

    public static void main(String[] args) throws InterruptedException {
//        int year = 2023;
//        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(VN_TIME_ZONE_STRING));
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        for(int i=1; i<=365; i++){
//            String date = sdf.format(getDateFromDayOfYear(cal, i));
//            int weekOfYear = getIso8601WeekOfYearFrom(i, year);
//            System.out.println("Backend date "+date+" - day "+i+" - week "+weekOfYear);
//        }
    }
}
