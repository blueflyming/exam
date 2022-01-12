package cool.scx.example.auth.exception;

/**
 * 认证异常
 *
 * @author scx567888
 * @version 0.3.6
 */
public abstract class AuthException extends RuntimeException {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessage() {
        return "LoginException";
    }

}
