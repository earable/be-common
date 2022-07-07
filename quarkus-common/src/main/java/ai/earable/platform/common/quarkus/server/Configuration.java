package ai.earable.platform.common.quarkus.server;

import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.WebClient;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by BinhNH on 3/17/22
 */
@ApplicationScoped
@Slf4j
public class Configuration {
    @Inject
    protected  io.vertx.core.Vertx globalVertx;

    @Singleton
    public WebClient getWebClient(){ //TODO: Move config to properties file and configMap
        WebClientOptions webClientOptions = new WebClientOptions()
            .setMaxPoolSize(4) //IO threads handle sending and receiving
            .setConnectTimeout(1) //Maximum time to wait connection available
            .setIdleTimeout(100) // Maximum time before release connection if no data is received.
            .setKeepAlive(true)
            .setKeepAliveTimeout(5)  // Maximum time in pool of this connection before be evicted or closed.
            .setTcpKeepAlive(true)
            .setTcpNoDelay(true);
        return WebClient.create(new Vertx(globalVertx), webClientOptions);
    }
}
