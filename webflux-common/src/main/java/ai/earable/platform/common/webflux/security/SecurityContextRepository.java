package ai.earable.platform.common.webflux.security;

import ai.earable.platform.common.data.exception.EarableErrorCode;
import ai.earable.platform.common.data.exception.EarableException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SecurityContextRepository implements ServerSecurityContextRepository {

    private final EarableAuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private static final String BEARER = "Bearer ";

    @Override
    public Mono<Void> save(ServerWebExchange swe, SecurityContext sc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange swe) {
        return Mono.justOrEmpty(swe.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .filter(authHeader -> authHeader.startsWith(BEARER))
                .map(authHeader -> authHeader.substring(BEARER.length()))
                // check token expire
                .filter(token -> jwtUtils.isValidToken(token))
                // validate token on redis
                .flatMap(authToken -> jwtUtils.validateTokenOnCache(authToken)
                        .filter(isValid -> isValid)
                        // if token match on redis then return token to continue process
                        .map(b -> authToken))
                .switchIfEmpty(Mono.error(new EarableException(HttpStatus.UNAUTHORIZED.value(), EarableErrorCode.UNAUTHORIZED)))
                .flatMap(authToken -> {
                    Authentication authentication = jwtUtils.getAuthentication(authToken);
                    return this.authenticationManager.authenticate(authentication).map(newAuthentication -> {
                        SecurityContextHolder.setContext(new SecurityContextImpl(newAuthentication));
                        return SecurityContextHolder.getContext();
                    });
                });
    }
}