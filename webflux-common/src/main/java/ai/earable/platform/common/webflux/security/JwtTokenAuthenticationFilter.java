package ai.earable.platform.common.webflux.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * Created by BinhNH on 6/13/22
 */
@Component
@RequiredArgsConstructor
public class JwtTokenAuthenticationFilter implements WebFilter {

    public static final String HEADER_PREFIX = "Bearer ";
    private final JwtUtils jwtUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = resolveToken(exchange.getRequest());
        return Mono.justOrEmpty(resolveToken(exchange.getRequest()))
                .filter(tk -> StringUtils.hasText(token))
                // check token expired
                .filter(tk ->  !jwtUtils.isTokenExpired(token))
                // check token on redis
                .flatMap(tk -> jwtUtils.validateTokenOnCache(token))
                .filter(isValid -> isValid)
                .flatMap(b -> {
                    Authentication authentication = this.jwtUtils.getAuthentication(token);
                    return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
                })
                .switchIfEmpty(chain.filter(exchange));
    }

    private String resolveToken(ServerHttpRequest serverHttpRequest) {
        String bearerToken = serverHttpRequest.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(HEADER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
