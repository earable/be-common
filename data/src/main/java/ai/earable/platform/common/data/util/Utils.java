package ai.earable.platform.common.data.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerWebExchange;

import java.util.UUID;

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
}
