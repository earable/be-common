package ai.earable.platform.common.utils;

import ai.earable.platform.common.data.session.model.Session;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerWebExchange;

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

}
