package ai.earable.platform.common.webflux.security;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import reactor.core.publisher.Mono;

/**
 * Created by BinhNH on 6/14/22
 */
@Component
@RequestScope
public class ReactiveSecurityContextUtils {
    @Autowired
    protected JwtUtils jwtUtils;

    public Mono<String> getUserId(){
        return ReactiveSecurityContextHolder.getContext().map(securityContext -> {
            String token = (String) securityContext.getAuthentication().getCredentials();
            Claims claims = jwtUtils.getAllClaimsFromToken(token);
            return claims.get("user_id").toString(); //TODO: Handle error cases
        });
    }
}
