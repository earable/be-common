package ai.earable.platform.common.data.subscription;

import ai.earable.platform.common.data.subscription.auth.SubscriptionAuthentication;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.NamingStrategy;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import com.datastax.oss.driver.api.mapper.annotations.PropertyStrategy;
import com.datastax.oss.driver.api.mapper.entity.naming.NamingConvention;

import java.net.URI;

/**
 * Created by BinhNH on 3/24/2022
 */
@Entity
@PropertyStrategy(mutable = false)
@NamingStrategy(convention = NamingConvention.UPPER_SNAKE_CASE)
public class DataSubscription {
    @PartitionKey
    private String id;
    private DataNotificationFilter filter;
    private URI callbackUri;
    private SubscriptionAuthentication authentication;

    public DataSubscription(){}

    public DataSubscription(String id, DataNotificationFilter filter,
                            URI callbackUri, SubscriptionAuthentication authentication){
        this.id = id;
        this.filter = filter;
        this.callbackUri = callbackUri;
        this.authentication = authentication;

    }

    public String getId() {
        return id;
    }

    public DataNotificationFilter getFilter() {
        return filter;
    }

    public URI getCallbackUri() {
        return callbackUri;
    }

    public SubscriptionAuthentication getAuthentication() {
        return authentication;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFilter(DataNotificationFilter filter) {
        this.filter = filter;
    }

    public void setCallbackUri(URI callbackUri) {
        this.callbackUri = callbackUri;
    }

    public void setAuthentication(SubscriptionAuthentication authentication) {
        this.authentication = authentication;
    }
}
