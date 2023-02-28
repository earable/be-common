package ai.earable.platform.common.webflux.security;

import ai.earable.platform.common.data.audit.AuditLog;
import ai.earable.platform.common.data.audit.AuditType;
import ai.earable.platform.common.data.constant.ClaimsConstant;
import ai.earable.platform.common.data.exception.EarableErrorCode;
import ai.earable.platform.common.data.exception.EarableException;
import ai.earable.platform.common.data.user.enums.RoleType;
import ai.earable.platform.common.webflux.utils.MessageUtils;
import ai.earable.platform.common.webflux.utils.RedisUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SecurityContextRepository implements ServerSecurityContextRepository {

    private final EarableAuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private static final String BEARER = "Bearer ";

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private MessageUtils messageUtils;

    @Override
    public Mono<Void> save(ServerWebExchange swe, SecurityContext sc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange swe) {

        messageUtils.setLocaleContext(Locale.ENGLISH.getLanguage());
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
                    return redisUtils.getLanguage(authToken).switchIfEmpty(Mono.just("")).flatMap(language -> {
                        if (!StringUtils.isBlank(language)) {
                            messageUtils.setLocaleContext(language);
                        }
                        Claims claims = jwtUtils.getAllClaimsFromToken(authToken);
                        List<String> roleNames = (List<String>) claims.get(ClaimsConstant.ROLE);
                        String userId = claims.get(ClaimsConstant.USER_ID, String.class);
                        if (!roleNames.isEmpty() && !StringUtils.isBlank(userId) && roleNames.stream().anyMatch(r -> r.equals(RoleType.ROLE_CUSTOMER.name()))) {
                            redisUtils.addAuditLog(AuditLog.builder().createdAt(new Timestamp(System.currentTimeMillis())).auditType(AuditType.API_REQUEST).userId(UUID.fromString(userId)).apiUrlRequest(swe.getRequest().getURI().toString()).build());
                        }
                        Authentication authentication = jwtUtils.getAuthentication(authToken);
                        return this.authenticationManager.authenticate(authentication).map(SecurityContextImpl::new);
                    });
                });
    }
}