package cn.org.imaginary.common.exception;

/**
 * Author    : imaginary
 * Date      : 2017/5/23 14 40
 * Version   : V1.0
 * Desc      :
 */
public class MailAuthenticationException extends MailException {
    /**
     * Constructor for MailAuthenticationException.
     * @param msg message
     */
    public MailAuthenticationException(String msg) {
        super(msg);
    }

    /**
     * Constructor for MailAuthenticationException.
     * @param msg the detail message
     * @param cause the root cause from the mail API in use
     */
    public MailAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * Constructor for MailAuthenticationException.
     * @param cause the root cause from the mail API in use
     */
    public MailAuthenticationException(Throwable cause) {
        super("Authentication failed", cause);
    }
}
