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

    @Autowired
    private ReactiveRedisTemplate<String, String> redisTemplate;
    @Autowired
    protected JwtUtils jwtUtils;

    public Mono<Boolean> saveLanguage(String token, String language) {
        Claims claims = jwtUtils.getAllClaimsFromToken(token);

        String userId = claims.get("user_id", String.class);
        String key = String.format("%s_language", userId);
        Duration duration = Duration.ofMillis(claims.getExpiration().getTime() - System.currentTimeMillis());
        return redisTemplate.opsForValue().set(key, language, duration);
    }

    public Mono<String> getLanguage(String token) {
        Claims claims = jwtUtils.getAllClaimsFromToken(token);
        String userId = claims.get("user_id", String.class);
        String key = String.format("%s_language", userId);
        return redisTemplate.opsForValue().get(key).switchIfEmpty(Mono.just(Locale.ENGLISH.getLanguage()));
    }
}
