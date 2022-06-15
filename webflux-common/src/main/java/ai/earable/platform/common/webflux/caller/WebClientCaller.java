package ai.earable.platform.common.webflux.caller;

import ai.earable.platform.common.data.exception.EarableErrorCode;
import ai.earable.platform.common.data.exception.EarableException;
import ai.earable.platform.common.data.exception.ErrorDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Created by BinhNH on 3/30/2022
 */
@Service
@Slf4j
public class WebClientCaller implements Caller{
    @Autowired WebClient webClient;

    @Override
    public <V> Mono<V> getMono(String calledUriTemplate, Class<V> responseType) {
        return webClient.method(HttpMethod.GET)
                .uri(calledUriTemplate)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse ->
                    convertToMonoResponse(clientResponse, responseType));
    }

    @Override
    public <V> Mono<V> getMono(String calledUriTemplate, Class<V> responseType, String... params) {
        return webClient.method(HttpMethod.GET)
                .uri(calledUriTemplate, params)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse ->
                    convertToMonoResponse(clientResponse, responseType));
    }

    @Override
    public <V> Mono<V> getMono(String calledUriTemplate, Class<V> responseType, MultiValueMap<String, String> multiValueMap, String... params) {
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(calledUriTemplate).queryParams(multiValueMap).encode().toUriString();
        return webClient
                .get()
                .uri(urlTemplate, params)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> convertToMonoResponse(clientResponse, responseType));
    }

    @Override
    public <V> Mono<V> requestToMono(HttpMethod method, String calledUri, String bearerToken, Class<V> responseType, String... params) {
        return webClient
                .method(method)
                .uri(calledUri, params)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", bearerToken)
                .exchangeToMono(clientResponse -> convertToMonoResponse(clientResponse, responseType));
    }

    @Override
    public <V> Flux<V> requestToFlux(HttpMethod method, String calledUri, String bearerToken, Class<V> responseType, String... params) {
        return webClient
                .method(method)
                .uri(calledUri, params)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", bearerToken)
                .retrieve()
                .bodyToFlux(responseType);
    }

    @Override
    public <T, V> Mono<V> requestToMono(HttpMethod method, String calledUri, T requestBody, Class<T> requestType, Class<V> responseType) {
        return webClient.method(method)
                .uri(calledUri)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(requestBody), requestType)
                .exchangeToMono(clientResponse -> convertToMonoResponse(clientResponse, responseType));
    }

    @Override
    public <T, V> Mono<V> requestToMono(HttpMethod method, String calledUri, T requestBody, Class<T> requestType, Class<V> responseType, String... params) {
        return webClient.method(method)
                .uri(calledUri, params)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(requestBody), requestType)
                .exchangeToMono(clientResponse -> convertToMonoResponse(clientResponse, responseType));
    }

    @Override
    public <T, V> Mono<V> requestToMono(HttpMethod method, String calledUri, MultiValueMap<String, T> multiValueMap, Class<V> responseType, String... params) {
        return webClient.method(method)
                .uri(calledUri)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromMultipartData(multiValueMap))
                .exchangeToMono(clientResponse -> convertToMonoResponse(clientResponse, responseType));
    }

    @Override
    public <V> Flux<V> getFlux(HttpMethod method, String calledUri,  Class<V> responseType) {
        return webClient.method(method)
                .uri(calledUri)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToFlux(clientResponse -> convertToFluxResponse(clientResponse, responseType));
    }

    @Override
    public <V> Flux<V> getFlux(HttpMethod method, String calledUri, Class<V> responseType, String... params) {
        return webClient.method(method)
                .uri(calledUri, params)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToFlux(clientResponse -> convertToFluxResponse(clientResponse, responseType));
    }

    @Override
    public <V> Flux<V> getFlux(HttpMethod method, String calledUri, Class<V> responseType,
                               Map<String, String> headers, String... params) {
        return webClient.method(method)
                .uri(calledUri, params)
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
}
