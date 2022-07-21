package ai.earable.platform.common.webflux.caller;

import ai.earable.platform.common.data.exception.EarableErrorCode;
import ai.earable.platform.common.data.exception.EarableException;
import ai.earable.platform.common.data.exception.ErrorDetails;
import ai.earable.platform.common.data.http.HttpMethod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    private static final long DEFAULT_TIME_OUT = 30000; //TODO: Move to config map
    private final WebClient webClient;

    @Override
    public <V> Mono<V> getMono(String uri, Class<V> responseType) {
        return getMono(uri, responseType, DEFAULT_TIME_OUT);
    }

    @Override
    public <V> Mono<V> getMono(String uri, Class<V> responseType, long timeout) {
        return webClient.method(org.springframework.http.HttpMethod.GET)
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> convertToMonoResponse(clientResponse, responseType))
                .timeout(Duration.ofSeconds(timeout));
    }

    @Override
    public <V> Mono<V> getMono(String uri, Class<V> responseType, String... params) {
        return webClient.method(org.springframework.http.HttpMethod.GET)
                .uri(uri, params)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse ->
                    convertToMonoResponse(clientResponse, responseType));
    }

    @Override
    public <V> Mono<V> getMono(String uri, Map<String, String> headers, Class<V> responseType, long timeout, String... pathParams) {
        return webClient.method(org.springframework.http.HttpMethod.GET)
                .uri(uri, pathParams)
                .accept(MediaType.APPLICATION_JSON)
                .headers(httpHeaders -> headers.forEach(httpHeaders::set))
                .exchangeToMono(clientResponse -> convertToMonoResponse(clientResponse, responseType))
                .timeout(Duration.ofSeconds(timeout));
    }

    @Override
    public <V> Mono<V> getMono(String uri, Class<V> responseType, Map<String, String> headers, String... params) {
        return getMono(uri, headers, responseType, DEFAULT_TIME_OUT, params);
    }

    @Override
    public <V> Mono<V> getMono(String uri, Class<V> responseType, MultiValueMap<String, String> multiValueMap, String... params) {
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(uri).queryParams(multiValueMap).encode().toUriString();
        return webClient.get().uri(urlTemplate, params)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> 
                    convertToMonoResponse(clientResponse, responseType));
    }

    @Override
    public <V> Mono<V> requestToMono(HttpMethod method, String uri, String bearerToken, Class<V> responseType, String... params) {
        return webClient.method(wrap(method))
                .uri(uri, params)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", bearerToken)
                .exchangeToMono(clientResponse -> convertToMonoResponse(clientResponse, responseType));
    }

    @Override
    public <V> Flux<V> requestToFlux(HttpMethod method, String uri, String bearerToken, Class<V> responseType, String... params) {
        return webClient.method(wrap(method))
                .uri(uri, params)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", bearerToken)
                .retrieve()
                .bodyToFlux(responseType);
    }

    @Override
    public <T, V> Mono<V> requestToMono(HttpMethod method, String uri, T requestBody, Class<T> requestType, Class<V> responseType) {
        return webClient.method(wrap(method))
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(requestBody), requestType)
                .exchangeToMono(clientResponse -> convertToMonoResponse(clientResponse, responseType));
    }

    @Override
    public <V> Mono<V> requestToMono(HttpMethod method,
                                     String uri,
                                     Map<String, String> headers,
                                     MultipartBodyBuilder multipartBodyBuilder,
                                     Class<V> responseType) {
        return webClient.method(wrap(method))
                .uri(uri)
                .headers(httpHeaders -> headers.forEach(httpHeaders::set))
                .body(BodyInserters.fromMultipartData(multipartBodyBuilder.build()))
                .exchangeToMono(clientResponse -> convertToMonoResponse(clientResponse, responseType));
    }

    @Override
    public <T, V> Mono<V> requestToMono(HttpMethod method, String uri, T requestBody, Class<T> requestType, Class<V> responseType, String... params) {
        return webClient.method(wrap(method))
                .uri(uri, params)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(requestBody), requestType)
                .exchangeToMono(clientResponse -> convertToMonoResponse(clientResponse, responseType));
    }

    @Override
    public <T, V> Mono<V> requestToMono(HttpMethod method, String uri, MultiValueMap<String, T> bodyMap, Class<V> responseType, String... params) {
        return webClient.method(wrap(method))
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromMultipartData(bodyMap))
                .exchangeToMono(clientResponse -> convertToMonoResponse(clientResponse, responseType));
    }

    @Override
    public <T, V> Mono<V> requestToMono(HttpMethod method, String uri, String bearerToken, T requestBody, Class<T> requestType, Class<V> responseType) {
        return webClient.method(wrap(method))
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", bearerToken)
                .body(Mono.just(requestBody), requestType)
                .exchangeToMono(clientResponse -> convertToMonoResponse(clientResponse, responseType));
    }

    @Override
    public <V> Flux<V> getFlux(HttpMethod method, String uri,  Class<V> responseType) {
        return webClient.method(wrap(method))
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToFlux(clientResponse -> convertToFluxResponse(clientResponse, responseType));
    }

    @Override
    public <V> Flux<V> getFlux(HttpMethod method, String uri, Class<V> responseType, String... params) {
        return webClient.method(wrap(method))
                .uri(uri, params)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToFlux(clientResponse -> convertToFluxResponse(clientResponse, responseType));
    }

    @Override
    public <V> Flux<V> getFlux(HttpMethod method, String uri, Class<V> responseType,
                               Map<String, String> headers, String... params) {
        return webClient.method(wrap(method))
                .uri(uri, params)
                .accept(MediaType.APPLICATION_JSON)
                .headers(httpHeaders -> headers.forEach(httpHeaders::set))
                .exchangeToFlux(clientResponse -> convertToFluxResponse(clientResponse, responseType));
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
            case POST: return org.springframework.http.HttpMethod.POST;
            case PUT: return org.springframework.http.HttpMethod.PUT;
            case PATCH: return org.springframework.http.HttpMethod.PATCH;
            case DELETE: return org.springframework.http.HttpMethod.DELETE;
            case OPTIONS: return org.springframework.http.HttpMethod.OPTIONS;
            case TRACE: return org.springframework.http.HttpMethod.TRACE;
            default: return org.springframework.http.HttpMethod.GET;
        }
    }
}
