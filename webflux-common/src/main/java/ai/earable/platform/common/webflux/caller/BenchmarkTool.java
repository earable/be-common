package ai.earable.platform.common.webflux.caller;

import ai.earable.platform.common.data.exception.EarableErrorCode;
import ai.earable.platform.common.data.exception.EarableException;
import ai.earable.platform.common.data.http.HttpMethod;
import ai.earable.platform.common.data.timeseries.dto.TimeseriesDataResponse;
import ai.earable.platform.common.data.timeseries.model.MonitoredDataValue;
import ai.earable.platform.common.utils.TimeUtils;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.resources.ConnectionProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class BenchmarkTool {
    private static final int nettyPoolMaxConnections = 500;
    private static final int httpServerEventLoop = 64;
    private static final int webClientEventLoop = 64;

    private static final ENV env = ENV.DEV;

    private static final String localHost = "http://localhost:8888";
    private static final String devHost = "https://api-test.eardev.xyz";
    private static final String stagingHost = "https://api.eardev.xyz";

    private static final String dmsRootPath = "/dms/api/v3";
    private static final String fmsRootPath = "/fms/api/v3";

    private static final String benchmarkingPath = "/benchmarking";

    private static final String localToken = "Bearer " + "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJtYW5oZGRAZWFyYWJsZS5haSIsInJvbGUiOltdLCJ0b2tlbl9pZCI6IjhhYzlhNzY1LTVjZTAtNDEwNS04MWM5LTMxOTlkYjdiZTUxMSIsInVzZXJfaWQiOiI2MGVmNTQ4YS04OGMxLTQwNzktOTA4ZC1hN2E2N2Y2NzhlN2EiLCJleHAiOjMzMjE0NDY0NTY3LCJpYXQiOjE2NTc1MTI1Njd9.OrWQ9ldp4YLMO5cWB34S1ZVwqqTFwn-HiMEFcpDn2MShEnGp8Gr0opLeJVO83vdzoJjmcSpRRUPYM1Vz4_OTQxwLOkSEB4beF4zdpBoHyeT5a0b2IuGSQYWYtEWQi8VVBtP_Bu7XBTme9OIEJguP2rvYJ89f-w91TUsnkH11z9XUT0u6r4v3MUwLOeSt1T0z55qGhFTELTj2JU93iCpCqpeIhXAwcQCeMAP60pdr5lK9N58aVxSI08kkrJFGisS9fxAIT-H4DBiMwEmZohJ4EwWXrQHBmS7opVjuPFh6MGyUV8xnrqG8tG1xaRFAxDLsT358u6QYCSpQq4m6O8maCg";
    private static final String devToken = "Bearer " + "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJiYW5ndHYxQHlvcG1haWwuY29tIiwicm9sZSI6W10sInRva2VuX2lkIjoiNmE4Y2QzNTktNjU3OS00ODQ2LThlOTQtMzU2ZGI3NTE3NzgyIiwidXNlcl9pZCI6IjlkN2UyNzNlLWRjMmEtNDExNy1iNjMxLWVlN2Y2MjJjOTBlNiIsImV4cCI6MzMyMTgzNTAxOTcsImlhdCI6MTY2MTM5ODE5N30.jjXe47bfnaIfMQ9-F7ecnA0um2AX3jzeWeeVha1RRz7ilwDWMjnFDXihbyy629rVw6B-0d449gdv-eRWMRC3KkAm6dI_VylF-KSblqreWdWmB8Mj6OSM1Lmb9ald7QPCGQrkaHaEwoMre1sGq6-logZ6aWLugKePhDcJ7NZ2FqxPxwILpJhmSn-6JcFsYybniAHmein3msqrEXFf8lp36FR2a13f5Ee0LW9FNkgFVrNhZdLzSGnUtesgmDpvGBP_LiAavbmv1UzEkiZTg_38RQ_WXyjXyM90h-1k2ldS0SEB2rFImzm_MOfgiAePuFDUiFYl2F1JtrleQxXqantTWQ";
    private static final String stagingToken = "Bearer " + "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJiaW5obmhAZWFyYWJsZS5haSIsInJvbGUiOltdLCJ0b2tlbl9pZCI6Ijk5ODRiZTQ1LWZiOTktNDVhZS05ZWVmLTBiOTg0Nzk2OWU3YyIsInVzZXJfaWQiOiJlYWYzN2Y0Ni0yMzU5LTRmMDEtOTU1NS0xNGZhODg2OTRkMjUiLCJleHAiOjMzMjE4NjI4Mzc3LCJpYXQiOjE2NjE2NzYzNzd9.YgUYXcyZvX-5GsYmGosuaatOe8rV9l6xXqfJoVbpk8jSFS5OTrUuN_tUvVRBo1XZop8VnTlJkbZquk1kffomIrWZUdU2YxiVuRFHaEvF8pbLMv7tFox0lKUZH7mSlFgnpN6vFL3loJAeJghRS_ikEJNMV45NmY38AhdFupQKzhKJys4pjhNokVsyLb10GuosOQ17mI1-RqUco6va9ZJaFvWrcXm1WjsAWaEOoZ6IfjBj6KPh8FrmxupKbgzu2N2umS5R-yiFaylXGeiJFi4rMhAKsSWR8yft-N114JIVL3l6WqicYzT-EceNSMmholl1tlG51E_3HwUJV8hY7MDQPg";

    private ReactorResourceFactory initReactorResourceFactory(NioEventLoopGroup nioEventLoopGroup){
        ReactorResourceFactory reactorResourceFactory = new ReactorResourceFactory();
        reactorResourceFactory.setUseGlobalResources(false);
        reactorResourceFactory.setLoopResources(b -> nioEventLoopGroup);
        reactorResourceFactory.setConnectionProvider(ConnectionProvider
            .builder("test-http-server-connection-pool")
            .maxConnections(nettyPoolMaxConnections)
            .pendingAcquireMaxCount(nettyPoolMaxConnections*2)
            .build());
        return reactorResourceFactory;
    }

    private NioEventLoopGroup init(int nThreads){
        return new NioEventLoopGroup(nThreads);
    }

    private ReactiveWebServerFactory reactiveWebServerFactory(){
        NioEventLoopGroup nioEventLoopGroup = init(httpServerEventLoop);
        ReactorResourceFactory reactorResourceFactory = initReactorResourceFactory(nioEventLoopGroup);
        NettyReactiveWebServerFactory factory = new NettyReactiveWebServerFactory();
        factory.setResourceFactory(reactorResourceFactory);
        return factory;
    }

    private WebClient init(){
        final int size = 20 * 1024 * 1024; // in Mb
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

    enum ENV {
        LOCAL,
        DEV,
        STAGING
    }

    private void testReadDataWithTimeTags(){
        WebClient webClient = init();

        WebClientCaller caller = new WebClientCaller(webClient);
        caller.setDefaultTimeout(15);
        caller.setDefaultRetryTimes(1);
        caller.setDefaultRetryDelay(1);

        AtomicReference<String> path = new AtomicReference<>(localHost+dmsRootPath+benchmarkingPath);
        if(env == ENV.DEV) path = new AtomicReference<>(devHost+dmsRootPath+benchmarkingPath);
        if(env == ENV.STAGING) path = new AtomicReference<>(stagingHost+dmsRootPath+benchmarkingPath);

        String nextHost = localHost+fmsRootPath+benchmarkingPath;
        if(env == ENV.DEV) nextHost = devHost+fmsRootPath+benchmarkingPath;
        if(env == ENV.STAGING) nextHost = stagingHost+fmsRootPath+benchmarkingPath;

        String token = localToken;
        if(env == ENV.DEV) token = devToken;
        if(env == ENV.STAGING) token = stagingToken;

        String readPath = "https://api.eardev.xyz" + "/des/api/v3/read/profile/{profileId}";
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("dayOfYear", "252");
        queryParams.add("featureFilter", "FOCUS");
        queryParams.add("metricFilter", "FOCUS_SCORE");

        String profileId = "47ab0f17-7592-4d70-bb97-5727dfef7a97";
        caller.requestToMono(HttpMethod.GET, readPath, stagingToken, queryParams, TimeseriesDataResponse.class, profileId)
            .flatMap(timeseriesDataResponse -> {
                List<List<String>> focusScoresList = new ArrayList<>();
                List<String> startedTimestamp = new ArrayList<>();
                timeseriesDataResponse.getData().getResult().forEach(result -> {
                    List<String> focusScores = new ArrayList<>();
                    result.getValues().forEach(value -> focusScores.add(value.get(1)));
                    focusScoresList.add(focusScores);

                    long firstTimestamp = Long.parseLong(result.getValues().get(0).get(0))*1000;
                    startedTimestamp.add(TimeUtils.getHourFromTimestamp(firstTimestamp, "Asia/Ho_Chi_Minh")+"");
                });
                return Mono.just(startedTimestamp);
            })
            .doOnSuccess(strings ->
                    System.out.println(strings.size()))
            .onErrorResume(throwable -> Mono.error(new EarableException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                EarableErrorCode.INTERNAL_SERVER_ERROR, throwable.getLocalizedMessage()))).block();

    }

    private void testLongestFocusTime(){
        WebClient webClient = init();

        WebClientCaller caller = new WebClientCaller(webClient);
        caller.setDefaultTimeout(15);
        caller.setDefaultRetryTimes(1);
        caller.setDefaultRetryDelay(1);

        AtomicReference<String> path = new AtomicReference<>(localHost+dmsRootPath+benchmarkingPath);
        if(env == ENV.DEV) path = new AtomicReference<>(devHost+dmsRootPath+benchmarkingPath);
        if(env == ENV.STAGING) path = new AtomicReference<>(stagingHost+dmsRootPath+benchmarkingPath);

        String nextHost = localHost+fmsRootPath+benchmarkingPath;
        if(env == ENV.DEV) nextHost = devHost+fmsRootPath+benchmarkingPath;
        if(env == ENV.STAGING) nextHost = stagingHost+fmsRootPath+benchmarkingPath;

        String token = localToken;
        if(env == ENV.DEV) token = devToken;
        if(env == ENV.STAGING) token = stagingToken;

        String readPath = "https://api.eardev.xyz" + "/des/api/v3/read/profile/{profileId}";
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("dayOfYear", "249");
        queryParams.add("featureFilter", "FOCUS");
        queryParams.add("metricFilter", "FOCUS_SCORE");

        String profileId = "47ab0f17-7592-4d70-bb97-5727dfef7a97";
        caller.requestToMono(HttpMethod.GET, readPath, stagingToken, queryParams, TimeseriesDataResponse.class, profileId)
                .flatMap(timeseriesDataResponse -> {
                    List<String> longestFocusHours = new ArrayList<>();
                    timeseriesDataResponse.getData().getResult().forEach(result -> {
                        longestFocusHours.add(countLongestFocusDuration(result.getValues())+"");
                    });
                    return Mono.just(longestFocusHours);
                })
                .doOnSuccess(strings ->
                        System.out.println(strings.size()))
                .onErrorResume(throwable -> Mono.error(new EarableException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        EarableErrorCode.INTERNAL_SERVER_ERROR, throwable.getLocalizedMessage()))).block();

    }

    /**
     * This method to count the longest duration of focus time in seconds from FOCUS_SCORE data
     */
    private long countLongestFocusDuration(List<List<String>> monitoredDataValues){
        double focusThreshold = 30.0;
        boolean isFocusing = false;
        long longestDuration = 0;
        long startTimestamp = 0;
        long lastTimestamp = 0;
        for (List<String> monitoredDataValue : monitoredDataValues) {
            if (Double.parseDouble(monitoredDataValue.get(1)) > focusThreshold) {
                if (!isFocusing) {
                    isFocusing = true;
                    startTimestamp = Long.parseLong(monitoredDataValue.get(0));
                }
                lastTimestamp = Long.parseLong(monitoredDataValue.get(0));
            } else {
                if (isFocusing) {
                    isFocusing = false;
                    lastTimestamp = Long.parseLong(monitoredDataValue.get(0));
                    long duration = lastTimestamp - startTimestamp;
                    if (duration > longestDuration) longestDuration = duration;
                }
            }
        }
        return longestDuration == 0 ? lastTimestamp - startTimestamp : longestDuration;
    }

    private void benchmark(){
        WebClient webClient = init();

        WebClientCaller caller = new WebClientCaller(webClient);
        caller.setDefaultTimeout(15);
        caller.setDefaultRetryTimes(1);
        caller.setDefaultRetryDelay(1);

        AtomicReference<String> path = new AtomicReference<>(localHost+dmsRootPath+benchmarkingPath);
        if(env == ENV.DEV) path = new AtomicReference<>(devHost+dmsRootPath+benchmarkingPath);
        if(env == ENV.STAGING) path = new AtomicReference<>(stagingHost+dmsRootPath+benchmarkingPath);

        String nextHost = localHost+fmsRootPath+benchmarkingPath;
        if(env == ENV.DEV) nextHost = devHost+fmsRootPath+benchmarkingPath;
        if(env == ENV.STAGING) nextHost = stagingHost+fmsRootPath+benchmarkingPath;

        String token = localToken;
        if(env == ENV.DEV) token = devToken;
        if(env == ENV.STAGING) token = stagingToken;

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("nextHost", nextHost);
        multiValueMap.add("isCheckWithDB", "true");

        AtomicLong failed = new AtomicLong(0);
        AtomicLong success = new AtomicLong(0);

        int max = 100;

        ExecutorService executorService = Executors.newFixedThreadPool(16);
        for(int i=0; i<max; i++){
            final String targetPath = path.get();
            final String finalToken = token;
            executorService.execute(() -> {
                log.info("============================");
                caller.requestToFlux(HttpMethod.GET, targetPath, finalToken, multiValueMap, String.class)
                    .next()
                    .doOnError(throwable -> log.error("Failed! {} - {}", failed.incrementAndGet(), throwable.getLocalizedMessage()))
                    .doOnSuccess(s -> log.info("Success! {}", success.incrementAndGet()))
                    .subscribe();
            });
        }
    }

    public static void main(String[] args) {
        BenchmarkTool benchmarkTool = new BenchmarkTool();
//        benchmarkTool.benchmark();
//        benchmarkTool.testReadDataWithTimeTags();
        benchmarkTool.testLongestFocusTime();
    }

}
