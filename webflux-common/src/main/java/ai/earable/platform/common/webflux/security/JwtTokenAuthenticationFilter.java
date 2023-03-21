package ai.earable.platform.common.webflux.security;

import ai.earable.platform.common.data.exception.EarableErrorCode;
import ai.earable.platform.common.data.exception.EarableException;
import com.datastax.oss.driver.shaded.guava.common.net.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Slf4j
@Component
public class JwtTokenAuthenticationFilter implements WebFilter {

    @Autowired
    private JwtUtils jwtUtils;

    private static final String BEARER = "Bearer ";

    @Value("${earable.blacklist.usernames:}")
    private String blacklistUsernames;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        try {
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            String userAgent = exchange.getRequest().getHeaders().getFirst("User-Agent");

            boolean isLogged = false;
            if (StringUtils.hasText(authHeader) && authHeader.length() > BEARER.length()) {
                Authentication authentication = jwtUtils.getAuthentication(authHeader.substring(BEARER.length()));

                String[] blacklistUsernames = this.blacklistUsernames.split(",");
                if (Arrays.stream(blacklistUsernames).anyMatch(u -> u.equalsIgnoreCase(authentication.getName()))) {
                    return Mono.error(new EarableException(400, EarableErrorCode.USERNAME_IS_NOT_PERMITTED, "Username is not permitted!"));
                }
                log.info("userAgent = " + userAgent + " userName = " + authentication.getName());

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
        return chain.filter(exchange);
    }
}
