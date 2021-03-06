package cn.org.imaginary.common.message;

import javax.activation.FileTypeMap;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

/**
 * Author    : imaginary
 * Date      : 2017/5/23 15 11
 * Version   : V1.0
 * Desc      :
 */
public class SmartMimeMessage extends MimeMessage {
    private final String defaultEncoding;

    private final FileTypeMap defaultFileTypeMap;

    /**
     * Create a new SmartMimeMessage.
     *
     * @param session            the JavaMail Session to create the message for
     * @param defaultEncoding    the default encoding, or {@code null} if none
     * @param defaultFileTypeMap the default FileTypeMap, or {@code null} if none
     */
    public SmartMimeMessage(Session session, String defaultEncoding, FileTypeMap defaultFileTypeMap) {
        super(session);
        this.defaultEncoding = defaultEncoding;
        this.defaultFileTypeMap = defaultFileTypeMap;
    }

    /**
     * Return the default encoding of this message, or {@code null} if none.
     */
    public final String getDefaultEncoding() {
        return this.defaultEncoding;
    }

    /**
     * Return the default FileTypeMap of this message, or {@code null} if none.
     */
    public final FileTypeMap getDefaultFileTypeMap() {
        return this.defaultFileTypeMap;
    }
}
