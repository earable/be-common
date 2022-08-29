package ai.earable.platform.common.webflux.security;

import ai.earable.platform.common.data.security.UserIdToTokenMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Created by BinhNH on 6/14/22
 */
@Component
public class ReactiveSecurityContextUtils {
    @Autowired
    protected JwtUtils jwtUtils;

    public Mono<String> getUserId(){
        return getToken().map(token -> jwtUtils.getAllClaimsFromToken(token).get("user_id").toString()); //TODO: Handle error cases
    }

    public Mono<String> getUserEmail(){
        return getToken().map(token -> jwtUtils.getAllClaimsFromToken(token).get("sub").toString()); //TODO: Handle error cases
    }

    private String getUserId(String token){
        return jwtUtils.getAllClaimsFromToken(token).get("user_id").toString(); //TODO: Handle error cases
    }

    private String getUserEmail(String token){
        return jwtUtils.getAllClaimsFromToken(token).get("sub").toString(); //TODO: Handle error cases
    }

    public Mono<String> getToken(){
        return ReactiveSecurityContextHolder.getContext().map(securityContext -> {
            return (String) securityContext.getAuthentication().getCredentials(); //TODO: Handle error cases
        });
    }

    public Mono<UserIdToTokenMap> getUserId2TokenMap(){
        return getToken().map(token -> UserIdToTokenMap.builder().userId(getUserId(token)).token(token).email(getUserEmail(token)).build());
    }
}
