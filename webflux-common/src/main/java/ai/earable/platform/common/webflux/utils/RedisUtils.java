package ai.earable.platform.common.webflux.utils;

import ai.earable.platform.common.webflux.security.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Locale;

@Slf4j
@Component
public class RedisUtils {
    private final String USER_SUB = "sub";
    private final Duration LANGUAGE_EXPIRY_TIME = Duration.ofDays(365);
    @Autowired
    private ReactiveRedisTemplate<String, String> redisTemplate;

    @Autowired
    protected JwtUtils jwtUtils;

    public Mono<Boolean> saveLanguage(String token, String language) {
        Claims claims = jwtUtils.getAllClaimsFromToken(token);

        String userName = claims.get(USER_SUB, String.class);
        String key = String.format("%s_language", userName);
        return redisTemplate.opsForValue().set(key, language, LANGUAGE_EXPIRY_TIME);
    }

    public Mono<Boolean> saveLanguage1(String email, String language) {

        String key = String.format("%s_language", email);
        return redisTemplate.opsForValue().set(key, language, LANGUAGE_EXPIRY_TIME);
    }

    public Mono<String> getLanguage(String token) {
        Claims claims = jwtUtils.getAllClaimsFromToken(token);
        String userName = claims.get(USER_SUB, String.class);
        String key = String.format("%s_language", userName);
        return redisTemplate.opsForValue().get(key).switchIfEmpty(Mono.just(Locale.ENGLISH.getLanguage()));
    }

    public Mono<String> getLanguageByUserName(String userName) {
        String key = String.format("%s_language", userName);
        return redisTemplate.opsForValue().get(key).switchIfEmpty(Mono.just(Locale.ENGLISH.getLanguage()));
    }
}
