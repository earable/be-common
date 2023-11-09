package ai.earable.platform.common.webflux.security;

import ai.earable.platform.common.data.common.HttpRequestInfo;
import ai.earable.platform.common.data.exception.EarableErrorCode;
import ai.earable.platform.common.data.exception.EarableException;
import com.datastax.oss.driver.shaded.guava.common.net.HttpHeaders;
import io.sentry.CustomSamplingContext;
import io.sentry.ITransaction;
import io.sentry.Sentry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebServerApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.server.adapter.DefaultServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenAuthenticationFilter implements WebFilter {

    @Autowired
    private JwtUtils jwtUtils;

    private static final String BEARER = "Bearer ";

    @Value("${earable.blacklist.usernames:}")
    private String blacklistUsernames;

    @Value("${earable.cache-requests-in-seconds:30}")
    private Integer cacheRequestsInSeconds;

    private static List<HttpRequestInfo> recentRequests = new ArrayList<>();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        try {

            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            String userAgent = exchange.getRequest().getHeaders().getFirst("User-Agent");
            exchange.getRequest().getHeaders().entrySet().forEach(authHeader1 -> {
                log.info("header key = " + authHeader1.getKey() + " value = " + authHeader1.getValue());

            });
            boolean isLogged = false;
            if (org.springframework.util.StringUtils.hasText(authHeader) && authHeader.length() > BEARER.length()) {
                Authentication authentication = jwtUtils.getAuthentication(authHeader.substring(BEARER.length()));

                String[] blacklistUsernames = this.blacklistUsernames.split(",");

                log.info("userAgent = " + userAgent + " userName = " + authentication.getName());
                if (Arrays.stream(blacklistUsernames).anyMatch(u -> u.equalsIgnoreCase(authentication.getName()))) {
                    return Mono.error(new EarableException(400, EarableErrorCode.USERNAME_IS_NOT_PERMITTED, "Username is not permitted!"));
                }
                SecurityContext context = new SecurityContextImpl(authentication);
                SecurityContextHolder.setContext(context);
                ReactiveSecurityContextHolder.withSecurityContext(Mono.just(context));

                isLogged = true;
            }
            if (!isLogged) {
                log.info("userAgent = " + userAgent);
            }
        } catch (Exception ex) {
            log.warn(ex.toString(), ex);
            SecurityContextHolder.clearContext();
            ReactiveSecurityContextHolder.clearContext();
        }
        String requestId = exchange.getRequest().getId();
        log.info("requestId = " + requestId);

        try {
            if (recentRequests.stream().anyMatch(httpRequestInfo -> httpRequestInfo.getRequestId().equals(requestId))) {
                return chain.filter(exchange).doOnEach(signal -> {
                    if (signal.isOnComplete() || signal.isOnError()) {
                        log.info("isOnComplete1 requestId = " + requestId);
                    }
                });
            }
            long timestamp = System.currentTimeMillis() - cacheRequestsInSeconds * 1000;
            synchronized (JwtTokenAuthenticationFilter.this) {
                recentRequests.add(0, HttpRequestInfo.builder().requestId(requestId).timestamp(System.currentTimeMillis()).build());
                recentRequests = recentRequests.stream().filter(httpRequestInfo -> {
                    return httpRequestInfo.getTimestamp() > timestamp;
                }).collect(Collectors.toList());
            }
            CustomSamplingContext context = new CustomSamplingContext();
            String fullPath = StringUtils.isEmpty(exchange.getRequest().getURI().getQuery()) ? exchange.getRequest().getURI().getPath() :
                    exchange.getRequest().getURI().getPath() + "?" + exchange.getRequest().getURI().getQuery();
            ITransaction transaction = Sentry.startTransaction(String.format("%s %s", exchange.getRequest().getMethod().name(), fullPath), exchange.getRequest().getMethod().name(), context);
            return chain.filter(exchange).doOnEach(signal -> {
                if (signal.isOnComplete() || signal.isOnError()) {
                    log.info("isOnComplete requestId = " + requestId);

                    transaction.finish();
                }
            });
        } catch (Exception ex) {
            log.warn(ex.toString(), ex);
            SecurityContextHolder.clearContext();
            ReactiveSecurityContextHolder.clearContext();
            return chain.filter(exchange).doOnEach(signal -> {
                if (signal.isOnComplete() || signal.isOnError()) {
                    log.info("isOnComplete2 requestId = " + requestId);
                }
            });
        }
    }
}
