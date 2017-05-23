package cn.org.imaginary.common;

import cn.org.imaginary.common.common.MimeMessageHelper;
import cn.org.imaginary.common.message.SimpleMailMessage;
import cn.org.imaginary.common.sender.JavaMailSenderImpl;
import org.junit.Before;
import org.junit.Test;

import javax.activation.DataSource;
import javax.activation.URLDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;

/**
 * Author    : imaginary
 * Date      : 2017/5/23 17 32
 * Version   : V1.0
 * Desc      :
 */
public class EmailTest {
    private JavaMailSenderImpl customMailSender;
    private final String from = "";
    private final String to = "";
    private final String host = "";
    private final String username = "";
    private final String password = "";
    private final int port = 25;

    private final String subject = "";
    private final String text = "";

    @Before
    public void init() {
        customMailSender = new JavaMailSenderImpl();
        customMailSender.setHost(host);
        customMailSender.setPort(port);
        customMailSender.setUsername(username);
        customMailSender.setPassword(password);
        customMailSender.setDefaultEncoding("utf-8");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        customMailSender.setJavaMailProperties(properties);
    }

    @Test
    public void testSendSimpleMessage() {
        customMailSender.send(getSimpleMsg());
    }

    @Test
    public void testSendMimeMessage() throws MalformedURLException, MessagingException {
        customMailSender.send(getMimeMsg());
    }

    private MimeMessage getMimeMsg() throws MessagingException, MalformedURLException {
        MimeMessage mimeMessage = customMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(new String[]{to});
        helper.setFrom(from);


        helper.setSubject(subject);
        helper.setText(text, false);

        helper.setSentDate(new Date());

        DataSource netDataSource = new URLDataSource(new URL("https://mvnrepository.com/assets/images/392dffac024b9632664e6f2c0cac6fe5-logo.png"));
        helper.addAttachment(netDataSource.getName(), netDataSource);
        return mimeMessage;
    }

    private SimpleMailMessage getSimpleMsg() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        return simpleMailMessage;
    }

}
