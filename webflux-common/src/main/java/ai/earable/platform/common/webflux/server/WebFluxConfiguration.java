package ai.earable.platform.common.webflux.server;

import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurerComposite;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.netty.resources.ConnectionProvider;

/**
 * Created by BinhNH on 3/27/2022
 */
@org.springframework.context.annotation.Configuration
@Component
@EnableAutoConfiguration
public class WebFluxConfiguration {
    @Value(value = "${earable.service.name}")
    private String serviceName;

    @Value(value = "${earable.nio-event-loop.http-server:64}")
    private int httpServerEventLoop;

    @Value(value = "${earable.nio-event-loop.webclient:64}")
    private int webClientEventLoop;

    @Value(value = "${earable.netty.pool.connections.max:2048}")
    private int nettyPoolMaxConnections;

    @Value(value = "${earable.netty.pool.connections.pending.max:4096}")
    private int nettyPoolMaxAcquirePending;

    @Value(value = "${earable.reactor.scheduler:4}")
    private int reactorScheduler;

    @Value(value = "${earable.webclient.codec.max-in-memory.size:48}")
    private int webClientMaxInMemorySize;

    @Bean
    public ReactiveWebServerFactory reactiveWebServerFactory(){
        NioEventLoopGroup nioEventLoopGroup = init(httpServerEventLoop);
        ReactorResourceFactory reactorResourceFactory = initReactorResourceFactory(nioEventLoopGroup);
        NettyReactiveWebServerFactory factory = new NettyReactiveWebServerFactory();
        factory.setResourceFactory(reactorResourceFactory);
        return factory;
    }

    @Bean
    public WebClient webClient(){
        final int size = webClientMaxInMemorySize * 1024 * 1024; // in Mb
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
            .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size)).build();
        NioEventLoopGroup nioEventLoopGroup = init(webClientEventLoop);
        ReactorResourceFactory reactorResourceFactory = initReactorResourceFactory(nioEventLoopGroup);
        ReactorClientHttpConnector reactorClientHttpConnector = new ReactorClientHttpConnector(reactorResourceFactory,
            httpClient -> httpClient);
        return WebClient.builder()
                .exchangeStrategies(strategies)
                .clientConnector(reactorClientHttpConnector)
                .build();
    }

    @Bean
    public Scheduler scheduler(){
        return Schedulers.boundedElastic();
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
        messageBundle.setBasenames("classpath:messages/messages");
        messageBundle.setDefaultEncoding("UTF-8");
        return messageBundle;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public WebFluxConfigurer corsConfigurer() {
        return new WebFluxConfigurerComposite() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
            }
        };
    }

    private NioEventLoopGroup init(int nThreads){
        return new NioEventLoopGroup(nThreads);
    }

    private ReactorResourceFactory initReactorResourceFactory(NioEventLoopGroup nioEventLoopGroup){
        ReactorResourceFactory reactorResourceFactory = new ReactorResourceFactory();
        reactorResourceFactory.setUseGlobalResources(false);
        reactorResourceFactory.setLoopResources(b -> nioEventLoopGroup);
        reactorResourceFactory.setConnectionProvider(ConnectionProvider
            .builder(serviceName+"-http-server-connection-pool")
            .maxConnections(nettyPoolMaxConnections)
            .pendingAcquireMaxCount(nettyPoolMaxAcquirePending)
            .build());
        return reactorResourceFactory;
    }
}
