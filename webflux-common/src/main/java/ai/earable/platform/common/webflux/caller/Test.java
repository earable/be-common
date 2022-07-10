package ai.earable.platform.common.webflux.caller;

import ai.earable.platform.common.data.exception.EarableErrorCode;
import ai.earable.platform.common.data.exception.EarableException;
import ai.earable.platform.common.data.timeseries.dto.MatrixDataResponse;
import ai.earable.platform.common.utils.UrlUtils;
import ai.earable.platform.common.webflux.server.WebFluxConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
public class Test {
    public static void main(String[] args) {
        WebFluxConfiguration configuration = new WebFluxConfiguration();
        WebClient webClient = configuration.webClient();

        Caller caller = new WebClientCaller(webClient);
        String query = "(lifetime(SLEEP_STAGES{sessionId=\"5F6138353B8A_1649821060000\"}[2d]) - duration_over_time((SLEEP_STAGES{sessionId =\"5F6138353B8A_1649821060000\"} == 4)[2d],30s) + (tlast_over_time((SLEEP_STAGES{sessionId =\"5F6138353B8A_1649821060000\"} == 4)[2d]) - tlast_over_time((SLEEP_STAGES{sessionId =\"5F6138353B8A_1649821060000\"} != 0 AND SLEEP_STAGES{sessionId =\"5F6138353B8A_1649821060000\"} != 4)[2d]))) / 3600";
        MatrixDataResponse matrixDataResponse = queryRange(caller, query, 1649828540, 1649828540).block();
        System.out.println("----------------");
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
}
