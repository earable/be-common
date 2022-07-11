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
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.netty.resources.ConnectionProvider;

import java.util.concurrent.Executors;

/**
 * Created by BinhNH on 3/27/2022
 */
@org.springframework.context.annotation.Configuration
@Component
@EnableAutoConfiguration
public class WebFluxConfiguration {
    @Value(value = "${earable.service.name}")
    private String serviceName;

    @Value(value = "${earable.nio-event-loop.http-server}")
    private int httpServerEventLoop;

    @Value(value = "${earable.nio-event-loop.webclient}")
    private int webClientEventLoop;

    @Value(value = "${earable.reactor.scheduler:4}")
    private int reactorScheduler;

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
        NioEventLoopGroup nioEventLoopGroup = init(webClientEventLoop);
        ReactorResourceFactory reactorResourceFactory = initReactorResourceFactory(nioEventLoopGroup);
        ReactorClientHttpConnector reactorClientHttpConnector = new ReactorClientHttpConnector(reactorResourceFactory, httpClient -> httpClient);
        return WebClient.builder().clientConnector(reactorClientHttpConnector).build();
    }

    @Bean
    public Scheduler scheduler(){
        return Schedulers.boundedElastic();
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageBundle =
                new ReloadableResourceBundleMessageSource();
        messageBundle.setBasenames("classpath:messages/messages");
        messageBundle.setDefaultEncoding("UTF-8");
        return messageBundle;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    private NioEventLoopGroup init(int nThreads){
        return new NioEventLoopGroup(nThreads);
    }

    private ReactorResourceFactory initReactorResourceFactory(NioEventLoopGroup nioEventLoopGroup){
        ReactorResourceFactory reactorResourceFactory = new ReactorResourceFactory();
        reactorResourceFactory.setUseGlobalResources(false);
        reactorResourceFactory.setLoopResources(b -> nioEventLoopGroup);
        reactorResourceFactory.setConnectionProvider(ConnectionProvider.builder(serviceName+"-http-server-connection-pool").build());
        return reactorResourceFactory;
    }
}
