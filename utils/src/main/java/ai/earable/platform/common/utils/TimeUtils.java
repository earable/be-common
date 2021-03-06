package ai.earable.platform.common.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by BinhNH on 3/28/2022
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

    public static void main(String[] args) {
        System.out.println(getNumberOfSecondsBetween(1657702497, 1657702527));
        System.out.println(getCurrentUnixTimestamp(AMERICA_TZ_STRING) + "\n" + getNextTimestamp(AMERICA_TZ_STRING, 3));
        System.out.println(getZoneOffset(System.currentTimeMillis(), AMERICA_TZ_STRING));
        System.out.println(convertFromUnixTimestamp(getNextTimestamp(AMERICA_TZ_STRING, 180)));
    }
}
