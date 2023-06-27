package ai.earable.platform.common.webflux.security;

import ai.earable.platform.common.data.security.UserIdToTokenMap;
import ai.earable.platform.common.webflux.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired
    private RedisUtils redisUtils;

    public Mono<String> getUserId() {
        return getToken().map(token -> jwtUtils.getAllClaimsFromToken(token).get("user_id").toString()); //TODO: Handle error cases
    }

    public Mono<String> getUserEmail() {
        return getToken().map(token -> getUserEmail(token));
    }

    public Mono<Long> getPhoneNumber() {
        return getToken().map(token -> getPhoneNumber(token));
    }

    private String getUserId(String token) {
        return jwtUtils.getAllClaimsFromToken(token).get("user_id").toString(); //TODO: Handle error cases
    }

    private String getUserEmail(String token) {
        String sub = jwtUtils.getAllClaimsFromToken(token).get("sub").toString();
        if (sub.contains("@")) {
            return sub;
        }
        return null;
    }

    private Long getPhoneNumber(String token) {
        String sub = jwtUtils.getAllClaimsFromToken(token).get("sub").toString();
        if (sub.contains("@")) {
            return null;
        }
        try {
            return Long.parseLong(sub);
        } catch (Exception ex) {
            return null;
        }
    }
    private String getSub(String token) {
        String sub = jwtUtils.getAllClaimsFromToken(token).get("sub").toString();
        return sub;
    }

    public Mono<String> getToken() {
        return ReactiveSecurityContextHolder.getContext().map(securityContext -> {
            return (String) securityContext.getAuthentication().getCredentials(); //TODO: Handle error cases
        });
    }

    public Mono<UserIdToTokenMap> getUserId2TokenMap() {
        return getToken().map(token -> UserIdToTokenMap.builder().userId(getUserId(token)).token(token).phoneNumber(getPhoneNumber(token)).email(getUserEmail(token)).build());
    }

    public Mono<String> getLanguageByUserName(String token) {
        String email = getUserEmail(token);
        String userName = StringUtils.isEmpty(email) ? getSub(token) : email;
        return redisUtils.getLanguageByUserName(userName);
    }
}
