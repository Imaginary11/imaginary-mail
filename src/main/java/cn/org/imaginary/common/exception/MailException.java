package cn.org.imaginary.common.exception;

/**
 * Author    : imaginary
 * Date      : 2017/5/23 13 50
 * Version   : V1.0
 * Desc      :
 */
public abstract class MailException extends NestedRuntimeException{
    /**
     * Constructor for MailException.
     * @param msg the detail message
     */
    public MailException(String msg) {
        super(msg);
    }

    /**
     * Constructor for MailException.
     * @param msg the detail message
     * @param cause the root cause from the mail API in use
     */
    public MailException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
