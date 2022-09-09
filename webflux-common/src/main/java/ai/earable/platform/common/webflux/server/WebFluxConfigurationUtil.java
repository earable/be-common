package ai.earable.platform.common.webflux.server;

import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.resources.LoopResources;

/**
 * Created by BinhNH on 9/9/2022
 */
final class WebFluxConfigurationUtil {
    public static NioEventLoopGroup init(int nThreads){
        return new NioEventLoopGroup(nThreads);
    }

    public static ConnectionProvider createConnectionProvider(String prefix, int maxConnection){
        return ConnectionProvider.builder(prefix)
                .maxConnections(maxConnection)
                .pendingAcquireMaxCount(maxConnection*2)
                .lifo().build();
    }

    public static ReactorResourceFactory initReactorResourceFactory(NioEventLoopGroup nioEventLoopGroup, ConnectionProvider connectionProvider){
        ReactorResourceFactory reactorResourceFactory = new ReactorResourceFactory();
        reactorResourceFactory.setUseGlobalResources(false);
        reactorResourceFactory.setLoopResources(b -> nioEventLoopGroup);
        reactorResourceFactory.setConnectionProvider(connectionProvider);
        return reactorResourceFactory;
    }

    public static ReactorResourceFactory initReactorResourceFactory(LoopResources loopResources, ConnectionProvider connectionProvider){
        ReactorResourceFactory reactorResourceFactory = new ReactorResourceFactory();
        reactorResourceFactory.setUseGlobalResources(false);
        reactorResourceFactory.setLoopResources(loopResources);
        reactorResourceFactory.setConnectionProvider(connectionProvider);
        return reactorResourceFactory;
    }
}
