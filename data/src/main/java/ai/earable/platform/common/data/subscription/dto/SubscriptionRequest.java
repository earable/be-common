package ai.earable.platform.common.data.subscription.dto;

import ai.earable.platform.common.data.subscription.auth.SubscriptionAuthentication;
import lombok.*;

import java.net.URI;

/**
 * Created by BinhNH on 3/24/2022
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubscriptionRequest<FILTER> {
    /**
     * The filter settings for this subscription, to define the subset of all notifications this subscription relates to.
     */
    protected FILTER filter;

    /**
     * The URI of the endpoint to send the notification to.
     */
    protected URI callbackUri;

    /**
     * Authentication parameters to configure the use of Authorization when sending notifications corresponding to this
     * subscription.
     *
     * This attribute shall only be present if the subscriber requires authorization of notifications.
     */
    protected SubscriptionAuthentication authentication;
}
