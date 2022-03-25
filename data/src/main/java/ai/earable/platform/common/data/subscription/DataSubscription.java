package ai.earable.platform.common.data.subscription;

import ai.earable.platform.common.data.subscription.auth.SubscriptionAuthentication;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;

import java.net.URI;

/**
 * Created by BinhNH on 3/24/2022
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeName("DataSubscription")
public class DataSubscription {
    @JsonProperty("_id")
    private String id;
    private DataNotificationFilter filter;
    private URI callbackUri;
    private SubscriptionAuthentication authentication;
}
