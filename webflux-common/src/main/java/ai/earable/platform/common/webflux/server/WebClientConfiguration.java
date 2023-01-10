package ai.earable.platform.common.webflux.server;

import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.resources.ConnectionProvider;

import java.util.concurrent.TimeUnit;

import static ai.earable.platform.common.webflux.server.WebFluxConfigurationUtil.init;

/**
 * Created by BinhNH on 9/9/2022
 */
@Configuration
@Component
public class WebClientConfiguration {
    @Value(value = "${earable.nio-event-loop.webclient:64}")
    private int webClientEventLoop;

    @Value(value = "${earable.netty.pool.connections.max:1024}")
    private int nettyPoolMaxConnections;

    @Value(value = "${earable.webclient.codec.max-in-memory.size:48}")
    private int webClientMaxInMemorySize;

    @Value(value = "${earable.internal.caller.timeout:30}")
    private int defaultTimeout;

    //TODO: Config outside
    private static final int READ_TIMEOUT_SECONDS = 30;
    private static final int WRITE_TIMEOUT_SECONDS = 30;
    private static final int CONNECTION_TIMEOUT_MILLISECONDS = 30000;

    private ReactorClientHttpConnector reactorClientHttpConnector(){
        NioEventLoopGroup nioEventLoopGroup = init(webClientEventLoop);
        ConnectionProvider provider = WebFluxConfigurationUtil.createConnectionProvider("webclient-connection-pool", nettyPoolMaxConnections);
        ReactorResourceFactory reactorResourceFactory = WebFluxConfigurationUtil.initReactorResourceFactory(nioEventLoopGroup, provider);
        return new ReactorClientHttpConnector(reactorResourceFactory, httpClient ->
                    httpClient.doOnConnected(connection ->
                        connection.addHandlerLast(new ReadTimeoutHandler(defaultTimeout, TimeUnit.SECONDS))
                            .addHandlerLast(new WriteTimeoutHandler(defaultTimeout)))
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, defaultTimeout*1000)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_KEEPALIVE, false));
    }

    @Bean
    public WebClient webClient(){
        final int size = webClientMaxInMemorySize * 1024 * 1024; // in MB
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size)).build();
        return WebClient.builder().clientConnector(reactorClientHttpConnector())
                .exchangeStrategies(strategies).build();
    }
}
