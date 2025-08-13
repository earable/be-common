package ai.earable.platform.common.utils;

import ai.earable.platform.common.data.session.model.Session;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerWebExchange;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * @author DungNT
 * @date 01/08/2022
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {

    public static String getClientIP(ServerWebExchange exchange) {
        try {
            return exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("getClientIP exception: " + ex.getMessage());
            return UUID.randomUUID().toString();
        }
    }

    public static String calculateVicQueryWindow(Session session) {
        if (session.getClientTimestamp() > 0) {
            long daysDiff =
                    TimeUnit.DAYS.convert(System.currentTimeMillis() - session.getClientTimestamp(), TimeUnit.MILLISECONDS);
            String window = (daysDiff + 2) + "d";
            return window;
        } else {
            return "1d";
        }
    }

    public static boolean stringsEqual(String s1, String s2, Boolean ignoreCase) {
        if (s1 == null) {
            s1 = "";
        }
        if (s2 == null) {
            s2 = "";
        }

        return ignoreCase ? s1.equalsIgnoreCase(s2) : s1.equals(s2);
    }

    public static String formatDateTime(Long datetime) {
        final String DATE_PATTERN = "yyyy/MM/dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        Date result = new Date(datetime);
        return sdf.format(result);
    }

    public static String formatDate1(LocalDate date) {
        final String DATE_PATTERN = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        Calendar calendar = Calendar.getInstance();
        calendar.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth(), 0, 0, 0);
        Date result = new Date(calendar.getTimeInMillis());

        return sdf.format(result);
    }
}
