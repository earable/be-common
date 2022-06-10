package ai.earable.platform.common.jwt;

/**
 * @author nhunh
 * @date 06/06/2022
 */
public class JWTParseException extends Exception {
    public JWTParseException() {
    }

    public JWTParseException(String message) {
        super(message);
    }

    public JWTParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public JWTParseException(Throwable cause) {
        super(cause);
    }
}