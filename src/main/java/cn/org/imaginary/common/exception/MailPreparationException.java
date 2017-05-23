package cn.org.imaginary.common.exception;

/**
 * Author    : imaginary
 * Date      : 2017/5/23 16 37
 * Version   : V1.0
 * Desc      :
 */
public class MailPreparationException extends MailException {

    /**
     * Constructor for MailPreparationException.
     * @param msg the detail message
     */
    public MailPreparationException(String msg) {
        super(msg);
    }

    /**
     * Constructor for MailPreparationException.
     * @param msg the detail message
     * @param cause the root cause from the mail API in use
     */
    public MailPreparationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public MailPreparationException(Throwable cause) {
        super("Could not prepare mail", cause);
    }

}