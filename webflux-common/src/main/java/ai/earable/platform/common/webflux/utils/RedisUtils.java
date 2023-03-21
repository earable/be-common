package ai.earable.platform.common.webflux.utils;

import ai.earable.platform.common.data.audit.AuditLog;
import ai.earable.platform.common.utils.JsonUtil;
import ai.earable.platform.common.webflux.redis.RedisKey;
import ai.earable.platform.common.webflux.security.JwtUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

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

    public void addAuditLog(AuditLog auditLog) {
        try {
            redisTemplate.opsForHash().put(RedisKey.AUDIT_LOG.name(), UUID.randomUUID().toString(), JsonUtil.convertToJson(auditLog)).subscribe();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public Flux<Map.Entry<UUID, AuditLog>> getAuditLogBatch() {
        return redisTemplate.opsForHash().entries(RedisKey.AUDIT_LOG.name()).flatMap(t -> {
            try {
                AuditLog auditLog = JsonUtil.convertJsonToObject(t.getValue().toString(), AuditLog.class);
                return Mono.just(Map.entry(UUID.fromString(t.getKey().toString()), auditLog));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return Mono.empty();
        });
    }

    public Mono<Boolean> deleteAuditLogBatch() {
        return redisTemplate.opsForHash().delete(RedisKey.AUDIT_LOG.name());
    }
}
