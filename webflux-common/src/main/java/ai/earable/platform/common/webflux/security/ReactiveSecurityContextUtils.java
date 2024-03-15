package ai.earable.platform.common.webflux.security;

import ai.earable.platform.common.data.security.UserIdToTokenMap;
import ai.earable.platform.common.webflux.utils.RedisUtils;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

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

    private String getUserId(Claims claims) {
        return claims.get("user_id").toString(); //TODO: Handle error cases
    }

    private String getUserEmail(String token) {
        String sub = jwtUtils.getAllClaimsFromToken(token).get("sub").toString();
        if (sub.contains("@")) {
            return sub;
        }
        return null;
    }

    private String getUserEmail(Claims claims) {
        String sub = claims.get("sub").toString();
        if (sub.contains("@")) {
            return sub;
        }
        return null;
    }

    private List<String> getRoles(Claims claims) {
        List<String> sub = (List<String>) claims.get("role");
        return sub;
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
    private Long getPhoneNumber(Claims claims) {
        String sub = claims.get("sub").toString();
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

        return getToken().map(token -> {
            Claims claims = jwtUtils.getAllClaimsFromToken(token);
            return UserIdToTokenMap.builder().userId(getUserId(claims)).token(token).phoneNumber(getPhoneNumber(claims)).email(getUserEmail(claims)).roles(getRoles(claims)).build();
        });
    }

    public Mono<String> getLanguageByUserName(String token) {
        String email = getUserEmail(token);
        String userName = StringUtils.isEmpty(email) ? getSub(token) : email;
        return redisUtils.getLanguageByUserName(userName);
    }

    public Boolean hasRole(String token, String roleName) {
        Claims claims = jwtUtils.getAllClaimsFromToken(token);
        List<String> roles = getRoles(claims);
        return roles.contains(roleName);
    }
}
