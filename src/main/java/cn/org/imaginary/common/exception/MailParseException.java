package cn.org.imaginary.common.exception;

/**
 * Author    : imaginary
 * Date      : 2017/5/23 13 51
 * Version   : V1.0
 * Desc      : Exception thrown if illegal message properties are encountered.
 */
public class MailParseException extends MailException {
    /**
     * Constructor for MailParseException.
     * @param msg the detail message
     */
    public MailParseException(String msg) {
        super(msg);
    }

    /**
     * Constructor for MailParseException.
     * @param msg the detail message
     * @param cause the root cause from the mail API in use
     */
    public MailParseException(String msg, Throwable cause) {
        super(msg, cause);
    }
    /**
     * Constructor for MailParseException.
     * @param cause the root cause from the mail API in use
     */
    public MailParseException(Throwable cause) {
        super("Could not parse mail", cause);
    }
}
