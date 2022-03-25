package ai.earable.platform.common.data.subscription.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URI;

/**
 * Created by BinhNH on 3/24/2022
 */
@Getter
@Setter
@NoArgsConstructor
public class SubscriptionAuthentication {
    private AuthType authType;
    private ParamBasic paramBasic;
    private Oauth2Credentials oauth2Credentials;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ParamBasic {
        private String userName;
        private String password;
        private String token;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Oauth2Credentials {
        private String clientId;
        private String clientPassword;
        private URI tokenEndpoint;
        private String token;
    }
}
