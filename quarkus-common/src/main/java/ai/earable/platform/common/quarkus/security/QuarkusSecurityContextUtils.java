package ai.earable.platform.common.quarkus.security;

import ai.earable.platform.common.data.exception.EarableErrorCode;
import ai.earable.platform.common.data.exception.EarableException;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import javax.enterprise.context.RequestScoped;

/**
 * Created by BinhNH on 6/14/22
 */
@Component
@RequestScoped
@Slf4j
public class QuarkusSecurityContextUtils {
    @Autowired
    protected JsonWebToken jsonWebToken;

    private static final String HEADER_PREFIX = "Bearer ";

    public Mono<String> getUserId(){
        try{
            if(jsonWebToken.getClaim("user_id") != null)
                return jsonWebToken.getClaim("user_id");
            log.error("Not found userId in token!");
            return Mono.error(new EarableException(401, EarableErrorCode.UNAUTHORIZED));
        }
        catch (Exception ex){
            log.error("Not found userId in token!");
            return Mono.error(new EarableException(401, EarableErrorCode.UNAUTHORIZED));
        }
    }

    public Mono<String> getToken(){
        if(jsonWebToken.getRawToken() != null){
            return Mono.just(resolveToken(jsonWebToken.getRawToken()));
        }
        return Mono.error(new EarableException(401, EarableErrorCode.UNAUTHORIZED));
    }

    private String resolveToken(String rawToken) {
        if (StringUtils.hasText(rawToken) && rawToken.startsWith(HEADER_PREFIX)) {
            return rawToken.substring(7);
        }
        return rawToken;
    }
}
