package ai.earable.platform.common.webflux.caller;

import ai.earable.platform.common.data.exception.EarableErrorCode;
import ai.earable.platform.common.data.exception.EarableException;
import ai.earable.platform.common.data.exception.ErrorDetails;
import ai.earable.platform.common.data.http.HttpMethod;
import io.netty.handler.timeout.TimeoutException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * Created by BinhNH on 3/30/2022
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class WebClientCaller implements SpringCaller {
    @Value(value = "${earable.internal.caller.timeout:15}")
    private int defaultTimeout;

    @Value(value = "${earable.internal.caller.retry.times:3}")
    private int defaultRetryTimes;

    @Value(value = "${earable.internal.caller.retry.delay:1}")
    private int defaultRetryDelay;

    private final WebClient webClient;

    private final ServerCodecConfigurer configurer;

    @Override
    public <V> Mono<V> getMono(String uri, Class<V> responseType) {
        return getMono(uri, responseType, defaultTimeout);
    }

    private <V> Mono<V> getMono(String uri, Class<V> responseType, int timeout) {
        return webClient.method(org.springframework.http.HttpMethod.GET)
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> convertToMonoResponse(clientResponse, responseType))
                .retryWhen(configRetry(HttpMethod.GET, uri, defaultRetryTimes, defaultRetryDelay))
                .timeout(Duration.ofSeconds(timeout))
                .retryWhen(configRetry(HttpMethod.GET, uri, defaultRetryTimes, defaultRetryDelay));
    }

    @Override
    public <V> Mono<V> getMono(String uri, Class<V> responseType, String... params) {
        return webClient.method(org.springframework.http.HttpMethod.GET)
                .uri(uri, params)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> convertToMonoResponse(clientResponse, responseType))
                .timeout(Duration.ofSeconds(defaultTimeout))
                .retryWhen(configRetry(HttpMethod.GET, uri, defaultRetryTimes, defaultRetryDelay));
    }

    private <V> Mono<V> getMono(String uri, Class<V> responseType, Map<String, String> headers,
                                int timeout, String... pathParams) {
        return webClient.method(org.springframework.http.HttpMethod.GET)
                .uri(uri, pathParams)
                .accept(MediaType.APPLICATION_JSON)
                .headers(httpHeaders -> headers.forEach(httpHeaders::set))
                .exchangeToMono(clientResponse -> convertToMonoResponse(clientResponse, responseType))
                .timeout(Duration.ofSeconds(timeout))
                .retryWhen(configRetry(HttpMethod.GET, uri, defaultRetryTimes, defaultRetryDelay));
    }

    @Override
    public <V> Mono<V> getMono(String uri, Class<V> responseType, Map<String, String> headers, String... params) {
        return getMono(uri, responseType, headers, defaultTimeout, params);
    }

    @Override
    public <V> Mono<V> getMono(String uri, Class<V> responseType, MultiValueMap<String, String> queryParams, String... params) {
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(uri).queryParams(queryParams).encode().toUriString();
        return webClient.get()
                .uri(urlTemplate, params)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> convertToMonoResponse(clientResponse, responseType))
                .timeout(Duration.ofSeconds(defaultTimeout))
                .retryWhen(configRetry(HttpMethod.GET, uri, defaultRetryTimes, defaultRetryDelay));
    }

    @Override
    public <V> Mono<V> requestToMono(HttpMethod method, String uri, String bearerToken, Class<V> responseType, String... params) {
        return webClient.method(wrap(method))
                .uri(uri, params)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", bearerToken)
                .exchangeToMono(clientResponse -> convertToMonoResponse(clientResponse, responseType))
                .timeout(Duration.ofSeconds(defaultTimeout))
                .retryWhen(configRetry(method, uri, defaultRetryTimes, defaultRetryDelay));
    }

    @Override
    public <V> Flux<V> requestToFlux(HttpMethod method, String uri, String bearerToken, Class<V> responseType, String... params) {
        return webClient.method(wrap(method))
                .uri(uri, params)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", bearerToken)
                .retrieve()
                .bodyToFlux(responseType)
                .timeout(Duration.ofSeconds(defaultTimeout))
                .retryWhen(configRetry(method, uri, defaultRetryTimes, defaultRetryDelay));
    }

    @Override
    public <T, V> Mono<V> requestToMono(HttpMethod method, String uri, T requestBody, Class<T> requestType, Class<V> responseType) {
        return webClient.method(wrap(method))
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(requestBody), requestType)
                .exchangeToMono(clientResponse -> convertToMonoResponse(clientResponse, responseType))
                .timeout(Duration.ofSeconds(defaultTimeout))
                .retryWhen(configRetry(method, uri, defaultRetryTimes, defaultRetryDelay));
    }

    @Override
    public <V> Mono<V> requestToMono(HttpMethod method, String uri, Map<String, String> headers,
                                     MultipartBodyBuilder multipartBodyBuilder, Class<V> responseType) {
        return webClient.method(wrap(method))
                .uri(uri)
                .header("Authorization", headers.get("Authorization"))
                .header("Content-Type", headers.get("Content-Type"))
                .body(BodyInserters.fromMultipartData(multipartBodyBuilder.build()))
                .exchangeToMono(clientResponse -> convertToMonoResponse(clientResponse, responseType))
                .timeout(Duration.ofSeconds(defaultTimeout));
    }

    @Override
    public <T, V> Mono<V> requestToMono(HttpMethod method, String uri, T requestBody, Class<T> requestType, Class<V> responseType, String... params) {
        return webClient.method(wrap(method))
                .uri(uri, params)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(requestBody), requestType)
                .exchangeToMono(clientResponse -> convertToMonoResponse(clientResponse, responseType))
                .timeout(Duration.ofSeconds(defaultTimeout))
                .retryWhen(configRetry(method, uri, defaultRetryTimes, defaultRetryDelay));
    }

    @Override
    public <T, V> Mono<V> requestToMono(HttpMethod method, String uri, MultiValueMap<String, T> multipartData, Class<V> responseType, String... params) {
        return webClient.method(wrap(method))
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromMultipartData(multipartData))
                .exchangeToMono(clientResponse -> convertToMonoResponse(clientResponse, responseType))
                .timeout(Duration.ofSeconds(defaultTimeout));
    }

    @Override
    public <V> Flux<V> requestToFlux(HttpMethod method, String uri, String bearerToken,
                                     MultiValueMap<String, String> queryParams, Class<V> responseType) {
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(uri).queryParams(queryParams).encode().toUriString();
        return webClient.method(wrap(method))
                .uri(urlTemplate)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", bearerToken)
                .exchangeToFlux(clientResponse -> convertToFluxResponse(clientResponse, responseType))
                .timeout(Duration.ofSeconds(defaultTimeout))
                .retryWhen(configRetry(method, uri, defaultRetryTimes, defaultRetryDelay));
    }

    @Override
    public <T, V> Mono<V> requestToMono(HttpMethod method, String uri, String bearerToken, T requestBody,
                                        Class<T> requestType, Class<V> responseType) {
        return webClient.method(wrap(method))
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", bearerToken)
                .body(Mono.just(requestBody), requestType)
                .exchangeToMono(clientResponse -> convertToMonoResponse(clientResponse, responseType))
                .timeout(Duration.ofSeconds(defaultTimeout))
                .retryWhen(configRetry(method, uri, defaultRetryTimes, defaultRetryDelay));
    }

    @Override
    public <V> Flux<V> getFlux(HttpMethod method, String uri,  Class<V> responseType) {
        return webClient.method(wrap(method))
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToFlux(clientResponse -> convertToFluxResponse(clientResponse, responseType))
                .timeout(Duration.ofSeconds(defaultTimeout))
                .retryWhen(configRetry(method, uri, defaultRetryTimes, defaultRetryDelay));
    }

    @Override
    public <V> Flux<V> getFlux(HttpMethod method, String uri, Class<V> responseType, String... params) {
        return webClient.method(wrap(method))
                .uri(uri, params)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToFlux(clientResponse -> convertToFluxResponse(clientResponse, responseType))
                .timeout(Duration.ofSeconds(defaultTimeout))
                .retryWhen(configRetry(method, uri, defaultRetryTimes, defaultRetryDelay));
    }

    @Override
    public <V> Flux<V> getFlux(HttpMethod method, String uri, Class<V> responseType,
                               Map<String, String> headers, String... params) {
        return webClient.method(wrap(method))
                .uri(uri, params)
                .accept(MediaType.APPLICATION_JSON)
                .headers(httpHeaders -> headers.forEach(httpHeaders::set))
                .exchangeToFlux(clientResponse -> convertToFluxResponse(clientResponse, responseType))
                .timeout(Duration.ofSeconds(defaultTimeout))
                .retryWhen(configRetry(method, uri, defaultRetryTimes, defaultRetryDelay));
    }

    private <V> Mono<V> convertToMonoResponse(ClientResponse clientResponse, Class<V> result){
        if(clientResponse.statusCode().isError())
            return clientResponse.bodyToMono(ErrorDetails.class)
                    .flatMap(errorDetails -> Mono.error(convertFrom(errorDetails)));
        return clientResponse.bodyToMono(result);
    }

    private <V> Flux<V> convertToFluxResponse(ClientResponse clientResponse, Class<V> result){
        if(clientResponse.statusCode().isError())
            return clientResponse.bodyToMono(ErrorDetails.class)
                    .flatMapMany(errorDetails -> Flux.error(convertFrom(errorDetails)));
        return clientResponse.bodyToFlux(result);
    }

    private EarableException convertFrom(ErrorDetails errorDetails){
        log.error("Rest API calling failed! The error code: {}, detail: {}!", errorDetails.getHttpStatusCode(), errorDetails.getDetails());
        return new EarableException(errorDetails.getHttpStatusCode(),
                EarableErrorCode.valueOf(errorDetails.getEarableErrorCode()), errorDetails.getDetails());
    }

    @Override
    public <V> Mono<ClientResponse> requestToClientResponse(HttpMethod method, String uri, String bearerToken, String... params) {
        return webClient
                .method(wrap(method))
                .uri(uri, params)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", bearerToken)
                .exchange();
    }

    @Override
    public <T, V> Flux<V> requestToFlux(HttpMethod method, String uri, String bearerToken, List<T> requestBody, Class<V> responseType) {
        return webClient.method(wrap(method))
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", bearerToken)
                .bodyValue(requestBody)
                .exchangeToFlux(clientResponse -> convertToFluxResponse(clientResponse, responseType));
    }

    private org.springframework.http.HttpMethod wrap(HttpMethod httpMethod){
        switch (httpMethod) {
            case HEAD: return org.springframework.http.HttpMethod.HEAD;
            case GET: return org.springframework.http.HttpMethod.GET;
            case POST: return org.springframework.http.HttpMethod.POST;
            case PUT: return org.springframework.http.HttpMethod.PUT;
            case PATCH: return org.springframework.http.HttpMethod.PATCH;
            case DELETE: return org.springframework.http.HttpMethod.DELETE;
            case TRACE: return org.springframework.http.HttpMethod.TRACE;
            default: return org.springframework.http.HttpMethod.OPTIONS;
        }
    }

    private static Retry configRetry(HttpMethod httpMethod, String uri, int numberOfRetries, int retryDelayInSecond){
        return Retry.fixedDelay(numberOfRetries, Duration.ofSeconds(retryDelayInSecond))
                .filter(WebClientCaller::needToRetry)
                .doAfterRetry(retrySignal -> log.warn("Retry {} request to {} in {}/{} because of {}", httpMethod, uri,
                    retrySignal.totalRetriesInARow()+1, numberOfRetries, retrySignal.failure().getLocalizedMessage()))
                .onRetryExhaustedThrow(((retryBackoffSpec, retrySignal) -> retrySignal.failure()));
    }

    private static boolean needToRetry(Throwable throwable){
        if(throwable.getCause() instanceof TimeoutException)
            return true;
        final String errorMess = throwable.getLocalizedMessage();
        if(errorMess != null){
            return errorMess.toLowerCase().contains("connection reset by peer")
                    || errorMess.toLowerCase().contains("connection refused");
        }
        return false;
    }
}
