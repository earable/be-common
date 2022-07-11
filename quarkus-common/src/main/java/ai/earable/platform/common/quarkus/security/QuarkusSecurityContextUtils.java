package ai.earable.platform.common.quarkus.security;

import ai.earable.platform.common.data.exception.EarableErrorCode;
import ai.earable.platform.common.data.exception.EarableException;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.SecurityContext;

/**
 * Created by BinhNH on 6/14/22
 */
@RequestScoped
@Slf4j
public class QuarkusSecurityContextUtils {
    @Inject
    protected JsonWebToken jsonWebToken;

    @Inject
    protected SecurityContext securityContext;

    public String getUserId(){
        String errorMessage = "Not found userId in token!";
        try{
            String userId = jsonWebToken.getClaim("user_id");
            if(userId != null)
                return userId;
            log.error(errorMessage);
            throw new EarableException(401, EarableErrorCode.UNAUTHORIZED.getErrorDetail(), errorMessage);
        }
        catch (Exception ex){
            log.error(errorMessage);
            throw new EarableException(401, EarableErrorCode.UNAUTHORIZED.getErrorDetail(), errorMessage);
        }
    }

    public String getToken(){
        String token = jsonWebToken.getRawToken();
        if(token != null){
            return token;
        }
        String errorMessage = "Can't get token!";
        log.error(errorMessage);
        throw new EarableException(401, EarableErrorCode.UNAUTHORIZED.getErrorDetail(), errorMessage);
    }

    public String getUserInfo(){
        String info = securityContext.getUserPrincipal().getName();
        if(info != null){
            return info;
        }
        String errorMessage = "Can't get user information in token!";
        log.error(errorMessage);
        throw new EarableException(401, EarableErrorCode.UNAUTHORIZED.getErrorDetail(), errorMessage);
    }
}
