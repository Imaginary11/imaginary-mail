package cn.org.imaginary.common.sender;

import cn.org.imaginary.common.exception.MailAuthenticationException;
import cn.org.imaginary.common.exception.MailException;
import cn.org.imaginary.common.exception.MailParseException;
import cn.org.imaginary.common.exception.MailSendException;
import cn.org.imaginary.common.message.SimpleMailMessage;

/**
 * Author    : imaginary
 * Date      : 2017/5/23 13 47
 * Version   : V1.0
 * Desc      :
 */
public interface MailSender {
    /**
     * Send the given simple mail message.
     * @param simpleMessages the message to send
     * @throws MailParseException in case of failure when parsing the message
     * @throws MailAuthenticationException in case of authentication failure
     * @throws MailSendException in case of failure when sending the message
     */
    void send(SimpleMailMessage simpleMessages) throws MailException;

    /**
     * Send the given array of simple mail messages in batch.
     * @param simpleMessages the messages to send
     * @throws MailParseException in case of failure when parsing a message
     * @throws MailAuthenticationException in case of authentication failure
     * @throws MailSendException in case of failure when sending a message
     */
    void send(SimpleMailMessage... simpleMessages) throws MailException;
}
