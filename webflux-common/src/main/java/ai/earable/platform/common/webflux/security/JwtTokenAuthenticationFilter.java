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
        String bearerToken = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        ServerHttpRequest.Builder mutatedRequestBuilder = exchange.getRequest().mutate();
        ServerHttpRequest mutatedRequest = bearerToken == null ? mutatedRequestBuilder.build() :
            mutatedRequestBuilder.header(HttpHeaders.AUTHORIZATION, bearerToken).build();
        ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();

        String token = resolveToken(bearerToken);
        return Mono.justOrEmpty(token)
                .filter(tk -> StringUtils.hasText(token))
                // check token expired
                .filter(tk ->  !jwtUtils.isTokenExpired(token))
                // check token on redis
                .flatMap(tk -> jwtUtils.validateTokenOnCache(token))
                .filter(isValid -> isValid)
                .flatMap(b -> {
                    Authentication authentication = this.jwtUtils.getAuthentication(token);
                    return chain.filter(mutatedExchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
                })
                .switchIfEmpty(chain.filter(mutatedExchange));
    }

    private String resolveToken(String bearerToken) {
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(HEADER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
