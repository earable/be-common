package ai.earable.platform.common.webflux.security;

import ai.earable.platform.common.data.exception.EarableErrorCode;
import ai.earable.platform.common.data.exception.EarableException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;

@Slf4j
@Component
public class JwtUtils {

    @Autowired
    private ReactiveRedisTemplate<String, String> redisTemplate;

    @Value("${earable.token.caching.redis.enable:false}")
    private boolean enableRedisTokenCaching;

    @Value("${earable.auth.public-key-file-path:}")
    private String publicKeyFilePath;

    private static final String AUTHORITIES_KEY = "role";

    private static final String TOKEN_TYPE = "token_type";

    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";

    private PublicKey publicKey;

    @Autowired
    private Environment env;

    @PostConstruct
    public void initialData() {
        log.info("Initialize data public key for env: {}, file: {}", env.getActiveProfiles(), publicKeyFilePath);
        if (!StringUtils.hasText(publicKeyFilePath)) {
            throw new RuntimeException("Missing key [earable.auth.public-key-file-path] on file properties");
        }

        try {
            /* Read file from disk */
            File publicKeyFile = new File(publicKeyFilePath);
            DataInputStream dis = new DataInputStream(new FileInputStream(publicKeyFile));
            byte[] keyBytes = new byte[(int) publicKeyFile.length()];
            dis.readFully(keyBytes);
            dis.close();

            String keyString = new String(keyBytes);
            keyString = keyString
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replaceAll(System.lineSeparator(), "")
                    .replace("-----END PUBLIC KEY-----", "");

            X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.getDecoder().decode(keyString));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(spec);
            log.info("Loaded public key: [{}]", publicKeyFilePath);
        } catch (IOException e) {
            log.error("File [{}] does not exist!", publicKeyFilePath);
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException | IllegalArgumentException e) {
            log.error("Property [earable.auth.public-key-file-path] is invalid Public Key Spec: [{}]", publicKeyFilePath);
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.error("JWT parse exception: {} file: {}", e.getMessage(), publicKeyFilePath);
            throw new RuntimeException(e);
        }
    }

    public Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException |
                IllegalArgumentException e) {
            log.error(e.getLocalizedMessage());
            throw new EarableException(HttpStatus.UNAUTHORIZED.value(), EarableErrorCode.UNAUTHORIZED, null);
        }
    }

    public Mono<Boolean> validateTokenOnCache(String token) {
        return Mono.justOrEmpty(token)
                .flatMap(tk -> enableRedisTokenCaching ? isTokenExistOnRedis(token) : Mono.just(true))
                .switchIfEmpty(Mono.just(false));
    }

    private Mono<Boolean> isTokenExistOnRedis(String token) {
        Claims claims = getAllClaimsFromToken(token);
        String tokenId = claims.get("token_id", String.class);
        String userId = claims.get("user_id", String.class);
        // check token is existed on redis
        return redisTemplate.opsForValue()
                .get(userId)
                .filter(savedTokenId -> ObjectUtils.isNotEmpty(userId) && ObjectUtils.isNotEmpty(savedTokenId))
                .filter(savedTokenId -> savedTokenId.equals(tokenId))
                .flatMap(s -> Mono.just(true))
                .switchIfEmpty(Mono.just(false));
    }

    public boolean isValidToken(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        final String tokenType = getTokenType(token);

        // Check token is expired time
        if (expiration.before(new Date())) return false;

        // Check Token type is ACCESS TOKEN
        return ACCESS_TOKEN.equals(tokenType);
    }

    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    public String getTokenType(String token) {
        return getAllClaimsFromToken(token).get(TOKEN_TYPE, String.class);
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getAllClaimsFromToken(token);
        Object roles = claims.get(AUTHORITIES_KEY);
        Collection<? extends GrantedAuthority> authorities = roles == null ? AuthorityUtils.NO_AUTHORITIES
                : AuthorityUtils.commaSeparatedStringToAuthorityList(roles.toString().replace("[", "").replace("]", ""));
        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
}
