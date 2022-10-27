package ai.earable.platform.common.webflux.server;

import ai.earable.platform.common.webflux.utils.WebFluxUtils;
import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import reactor.netty.resources.ConnectionProvider;

/**
 * Created by BinhNH on 9/9/2022
 */
@Configuration
public class WebServerConfiguration {
    @Value(value = "${earable.service.name}")
    private String serviceName;

    @Value(value = "${earable.nio-event-loop.http-server:64}")
    private int httpServerEventLoop;

    @Value(value = "${earable.netty.pool.connections.max:1024}")
    private int nettyPoolMaxConnections;

    @Bean
    public ReactiveWebServerFactory reactiveWebServerFactory() {
        NioEventLoopGroup nioEventLoopGroup = WebFluxUtils.init(httpServerEventLoop);
        ConnectionProvider connectionProvider = WebFluxUtils.createConnectionProvider(serviceName + "-server-connection-pool", nettyPoolMaxConnections);
        ReactorResourceFactory reactorResourceFactory = WebFluxUtils.initReactorResourceFactory(nioEventLoopGroup, connectionProvider);
        NettyReactiveWebServerFactory factory = new NettyReactiveWebServerFactory();
        factory.setResourceFactory(reactorResourceFactory);
        return factory;
    }
}
