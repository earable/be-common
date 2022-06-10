package ai.earable.platform.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Properties;

public class JwtUtils {
    private Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    private static final JwtUtils INSTANCE = new JwtUtils();

    private static PublicKey publicKey;

    private JwtUtils() {
        logger.info("Loading authentication public key");
        final Properties properties = new Properties();
        String publicKeyFilePath = "";
        try (final InputStream stream =
                     this.getClass().getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(stream);
            publicKeyFilePath = (String) properties.get("earable.auth.public-key-file-path");

            if (publicKeyFilePath == null) {
                logger.error("Missing configuration [earable.auth.public-key-file-path]");
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
            logger.info("Loaded public key: [{}]", publicKeyFilePath);
        } catch (IOException e) {
            logger.error("File [{}] does not exist!", publicKeyFilePath);
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException | IllegalArgumentException e) {
            logger.error("Invalid Public Key Spec: [{}]", publicKeyFilePath);
            logger.error("Please check the file at configuration property [earable.auth.public-key-file-path] - [{}]",
                    publicKeyFilePath);
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static JwtUtils getInstance() {
        return INSTANCE;
    }

    public Claims decode(String jwt) throws JWTParseException {
        try {
            return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(jwt).getBody();
        } catch (Exception e) {
            throw new JWTParseException("Token is invalid!");
        }
    }
}
