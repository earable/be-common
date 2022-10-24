package ai.earable.platform.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PageRequestUtil {
    public static final String NEXT_PAGE_DATA_URL = "nextPageDataURL";


    public static String headerValue(ServerWebExchange exchange,
                                     int limit, int pageNumber) {
        Map<String, String> params = exchange.getRequest().getQueryParams().toSingleValueMap();
        final String LIMIT = "limit";
        final String PAGINGSTATE = "pagingState";
        params.put(LIMIT, Integer.toString(limit));
        params.put(PAGINGSTATE, Integer.toString(pageNumber));

        String path = exchange.getRequest().getPath() + "?";
        AtomicBoolean isFirst = new AtomicBoolean(true);
        for(Map.Entry<String, String> obj : params.entrySet()) {
            if (isFirst.get()) {
                isFirst.set(false);
                path += obj.getKey() + "=" + obj.getValue();
            } else {
                path += "&" + obj.getKey() + "=" + obj.getValue();
            }
        }

        return path;
    }
}
