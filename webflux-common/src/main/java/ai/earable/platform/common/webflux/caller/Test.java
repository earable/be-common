package ai.earable.platform.common.webflux.caller;

import ai.earable.platform.common.data.exception.EarableErrorCode;
import ai.earable.platform.common.data.exception.EarableException;
import ai.earable.platform.common.data.feature.dto.SessionOperationNotification;
import ai.earable.platform.common.data.feature.model.SessionEvent;
import ai.earable.platform.common.data.timeseries.dto.MatrixDataResponse;
import ai.earable.platform.common.data.timeseries.dto.VectorDataResponse;
import ai.earable.platform.common.utils.UrlUtils;
import ai.earable.platform.common.webflux.server.WebFluxConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
public class Test {
    public static void main(String[] args) {
        WebFluxConfiguration configuration = new WebFluxConfiguration();
        WebClient webClient = configuration.webClient();

        Caller caller = new WebClientCaller(webClient);

//        String query = "(lifetime(SLEEP_STAGES{sessionId=\"5F6138353B8A_1649821060000\"}[2d]) - duration_over_time((SLEEP_STAGES{sessionId =\"5F6138353B8A_1649821060000\"} == 4)[2d],30s) + (tlast_over_time((SLEEP_STAGES{sessionId =\"5F6138353B8A_1649821060000\"} == 4)[2d]) - tlast_over_time((SLEEP_STAGES{sessionId =\"5F6138353B8A_1649821060000\"} != 0 AND SLEEP_STAGES{sessionId =\"5F6138353B8A_1649821060000\"} != 4)[2d]))) / 3600";
//        MatrixDataResponse matrixDataResponse = queryRange(caller, query, 1649828540, 1649828540).block();
//        System.out.println("----------------");

//        final String baseUrl = "http://localhost:80/dms/api/v3/notifications/session-event";
//
//        caller.requestToMono(HttpMethod.POST, baseUrl, initFrom(), SessionOperationNotification.class, Void.class)
//            .onErrorResume(throwable -> {
//                log.error("[SESSION_ENDED] - Sending notification about session {} ended to DMS failed with message {}",
//                        "sessionId", throwable.getLocalizedMessage());
//                return Mono.error(new EarableException(HttpStatus.BAD_REQUEST.value(),
//                        EarableErrorCode.INTERNAL_SERVER_ERROR.toString(), throwable.getLocalizedMessage()));
//            }).doOnSuccess(s ->
//                    log.debug("[SESSION_ENDED] - Sending notification about session {} ended to DMS successfully", "sessionId")).block();
//        System.out.println("----------------");


        String totalTimeSessionQuery = "lifetime({__name__=\"SLEEP_STAGES\",sessionId=\"...\"}[5d])".replace("...", "6089203057A6_1657702437000");
        QueryStatistic queryStatistic = QueryStatistic.builder()
                .queryName("total_time_session")
                .query(totalTimeSessionQuery).build();

        query(caller, "6089203057A6_1657702437000", queryStatistic.getQuery())
            .flatMap(matrixDataResponse -> {
                Map<String, String> commonTags = matrixDataResponse.getData().getResult().get(0).getMetric();
                return Mono.empty();
            }).block();
    }

    private static SessionOperationNotification initFrom(){
        return SessionOperationNotification.builder()
                .userId("34234234")
                .profileId("34234234")
                .deviceId("34234234")
                .featureName("SLEEP")
                .timezone("Asia/Ho_Chi_Minh")
                .clientTimestamp(1657532965)
                .year(2022)
                .monthOfYear(4)
                .weekOfYear(15)
                .dayOfYear(103)
                .sessionId("5F6138353B8A_1649821060000")
                .event(SessionEvent.ENDED).build();
    }

    public static Mono<MatrixDataResponse> queryRange(Caller caller, String query, long start, long end) {
//        String readPath = "https://api.eardev.xyz/des/api/v3/read/query_range";
        String readPath = "http://localhost:80/des/api/v3/read/query_range";
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("query", UrlUtils.encode(query));
        multiValueMap.add("start", start+"");
        multiValueMap.add("end", end+"");

        return caller.getMono(readPath, MatrixDataResponse.class, multiValueMap)
                .filter(matrixDataResponse -> {
                    boolean filter = matrixDataResponse != null
                            && matrixDataResponse.getData().getResult() != null
                            && matrixDataResponse.getData().getResult().size() > 0;
                    if (!filter)
                        log.error("[PERFORMANCE_REPORT] - Can't read data of query {} from start {} to end {}", query, start, end);
                    return filter;
                })
                .onErrorResume(throwable -> {
                    log.error(throwable.getLocalizedMessage());
                    return Mono.error(new EarableException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            EarableErrorCode.INTERNAL_SERVER_ERROR));
                });
    }

    public static Mono<VectorDataResponse> query(Caller caller, String sessionId, String query) {
        String readPath = "https://api.eardev.xyz/des/api/v3/read/session/{sessionId}/query";
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("query", UrlUtils.encode(query));
        return caller.getMono(readPath, VectorDataResponse.class, multiValueMap, sessionId) //TODO: Using tokenFromFms
                .onErrorResume(throwable -> Mono.error(new EarableException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        EarableErrorCode.INTERNAL_SERVER_ERROR, throwable.getLocalizedMessage())));
    }
}
