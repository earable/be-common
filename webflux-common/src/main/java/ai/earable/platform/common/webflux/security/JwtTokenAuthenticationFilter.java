package ai.earable.platform.common.webflux.security;

import com.datastax.oss.driver.shaded.guava.common.net.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

@Slf4j
@Component
public class JwtTokenAuthenticationFilter implements WebFilter {

    @Autowired
    private JwtUtils jwtUtils;

    private static final String BEARER = "Bearer ";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        try {
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (StringUtils.hasText(authHeader)) {
                Authentication authentication = jwtUtils.getAuthentication(authHeader.substring(BEARER.length()));
                SecurityContext context = new SecurityContextImpl(authentication);
                SecurityContextHolder.setContext(context);
                ReactiveSecurityContextHolder.withSecurityContext(Mono.just(context));
            }
        } catch (Exception ex) {
            log.warn(ex.toString(), ex);
            SecurityContextHolder.clearContext();
            ReactiveSecurityContextHolder.clearContext();
        }
        return chain.filter(exchange);
    }
}
