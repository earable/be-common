package ai.earable.platform.common.webflux.security;

import ai.earable.platform.common.data.exception.EarableErrorCode;
import ai.earable.platform.common.data.exception.EarableException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

@Component
@Slf4j
public class JwtUtils {

    @Autowired
    private ReactiveRedisTemplate<String, String> redisTemplate;

    @Value("#{new Boolean('${earable.auth.ignore:false}')}")
    private boolean ignoreAuth;

    @Value("#{new Boolean('${earable.token.caching.enable:true}')}")
    private boolean enableCacheToken;

    private static final String AUTHORITIES_KEY = "role"; //Need to map to IMS
    private PublicKey publicKey = null;

    //TODO: Clean this method
    public JwtUtils() {
        log.info("Loading authentication public key");
        final Properties properties = new Properties();
        String publicKeyFilePath = "";
        try (final InputStream stream = this.getClass()
                .getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(stream);

            if (ignoreAuth) {
                log.info("Ignore authentication");
                return;
            }

            publicKeyFilePath = (String) properties.get("earable.auth.public-key-file-path");

            if (publicKeyFilePath == null) {
                log.error("Missing configuration [earable.auth.public-key-file-path]");
                throw new RuntimeException("Missing configuration [earable.auth.public-key-file-path]");
            }

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
            log.error("Invalid Public Key Spec: [{}]", publicKeyFilePath);
            log.error("Please check the file at configuration property [earable.auth.public-key-file-path] - [{}]",
                    publicKeyFilePath);
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            log.error(e.getLocalizedMessage());
            throw new EarableException(HttpStatus.UNAUTHORIZED.value(), EarableErrorCode.UNAUTHORIZED, null);
        }
    }

    public Mono<Boolean> validateTokenOnCache(String token) {
        return Mono.justOrEmpty(token)
                .flatMap(tk -> enableCacheToken ? isTokenExistOnRedis(token) : Mono.just(true))
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

    public boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getAllClaimsFromToken(token);
        Object authoritiesClaim = claims.get(AUTHORITIES_KEY);
        Collection<? extends GrantedAuthority> authorities = authoritiesClaim == null ? AuthorityUtils.NO_AUTHORITIES
                : AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesClaim.toString());
        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
}
