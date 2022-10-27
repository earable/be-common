package ai.earable.platform.common.webflux.server;

import ai.earable.platform.common.webflux.utils.WebFluxUtils;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.resources.ConnectionProvider;

import java.util.concurrent.TimeUnit;


/**
 * Created by BinhNH on 9/9/2022
 */
@Slf4j
@Configuration
public class WebClientConfiguration {
    @Value(value = "${earable.nio-event-loop.webclient:64}")
    private int webClientEventLoop;

    @Value(value = "${earable.netty.pool.connections.max:1024}")
    private int nettyPoolMaxConnections;

    @Value(value = "${earable.webclient.codec.max-in-memory.size:48}")
    private int webClientMaxInMemorySize;

    //TODO: Config outside
    private static final int READ_TIMEOUT_SECONDS = 10;
    private static final int WRITE_TIMEOUT_SECONDS = 10;
    private static final int CONNECTION_TIMEOUT_MILLISECONDS = 10000;

    private ReactorClientHttpConnector reactorClientHttpConnector() {
        NioEventLoopGroup nioEventLoopGroup = WebFluxUtils.init(webClientEventLoop);
        ConnectionProvider provider = WebFluxUtils.createConnectionProvider("webclient-connection-pool", nettyPoolMaxConnections);
        ReactorResourceFactory reactorResourceFactory = WebFluxUtils.initReactorResourceFactory(nioEventLoopGroup, provider);
        return new ReactorClientHttpConnector(reactorResourceFactory, httpClient ->
                httpClient.doOnConnected(connection ->
                        connection.addHandlerLast(new ReadTimeoutHandler(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(WRITE_TIMEOUT_SECONDS)))
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, CONNECTION_TIMEOUT_MILLISECONDS)
                        .option(ChannelOption.TCP_NODELAY, true)
                        .option(ChannelOption.SO_KEEPALIVE, false));
    }

    @Bean
    public WebClient webClient() {
        final int size = webClientMaxInMemorySize * 1024 * 1024; // in MB
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size)).build();
        return WebClient.builder().clientConnector(reactorClientHttpConnector())
                .exchangeStrategies(strategies).build();
    }
}
