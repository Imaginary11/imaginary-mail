package cn.org.imaginary.common.message;

import cn.org.imaginary.common.exception.MailParseException;

import java.util.Date;

/**
 * Author    : imaginary
 * Date      : 2017/5/23 13 52
 * Version   : V1.0
 * Desc      :
 */
public interface MailMessage {
    void setFrom(String from) throws MailParseException;

    void setReplyTo(String replyTo) throws MailParseException;

    void setTo(String to) throws MailParseException;

    void setTo(String[] to) throws MailParseException;

    void setCc(String cc) throws MailParseException;

    void setCc(String[] cc) throws MailParseException;

    void setBcc(String bcc) throws MailParseException;

    void setBcc(String[] bcc) throws MailParseException;

    void setSentDate(Date sentDate) throws MailParseException;

    void setSubject(String subject) throws MailParseException;

    void setText(String text) throws MailParseException;

}
