package cn.org.imaginary.common.exception;

/**
 * Author    : imaginary
 * Date      : 2017/5/23 15 19
 * Version   : V1.0
 * Desc      :
 */
public abstract class NestedExceptionUtils {

    /**
     * Build a message for the given base message and root cause.
     * @param message the base message
     * @param cause the root cause
     * @return the full exception message
     */
    public static String buildMessage(String message, Throwable cause) {
        if (cause != null) {
            StringBuilder sb = new StringBuilder();
            if (message != null) {
                sb.append(message).append("; ");
            }
            sb.append("nested exception is ").append(cause);
            return sb.toString();
        }
        else {
            return message;
        }
    }

}
