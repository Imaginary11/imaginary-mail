package cn.org.imaginary.common.sender;

import cn.org.imaginary.common.common.ConfigurableMimeFileTypeMap;
import cn.org.imaginary.common.common.MimeMessageHelper;
import cn.org.imaginary.common.common.MimeMessagePreparator;
import cn.org.imaginary.common.exception.*;
import cn.org.imaginary.common.message.MimeMailMessage;
import cn.org.imaginary.common.message.SimpleMailMessage;
import cn.org.imaginary.common.message.SmartMimeMessage;
import cn.org.imaginary.common.util.Assert;

import javax.activation.FileTypeMap;
import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.*;

/**
 * Author    : imaginary
 * Date      : 2017/5/23 16 35
 * Version   : V1.0
 * Desc      :
 */
public class JavaMailSenderImpl implements JavaMailSender {
    /** The default protocol: 'smtp' */
    public static final String DEFAULT_PROTOCOL = "smtp";

    /** The default port: -1 */
    public static final int DEFAULT_PORT = -1;

    private static final String HEADER_MESSAGE_ID = "Message-ID";


    private Properties javaMailProperties = new Properties();

    private Session session;

    private String protocol;

    private String host;

    private int port = DEFAULT_PORT;

    private String username;

    private String password;

    private String defaultEncoding;

    private FileTypeMap defaultFileTypeMap;

    /**
     * Create a new instance of the {@code JavaMailSenderImpl} class.
     * <p>Initializes the {@link #setDefaultFileTypeMap "defaultFileTypeMap"}
     * property with a default {@link ConfigurableMimeFileTypeMap}.
     */
    public JavaMailSenderImpl() {
        ConfigurableMimeFileTypeMap fileTypeMap = new ConfigurableMimeFileTypeMap();
        fileTypeMap.afterPropertiesSet();
        this.defaultFileTypeMap = fileTypeMap;
    }


    /**
     * Set JavaMail properties for the {@code Session}.
     * <p>A new {@code Session} will be created with those properties.
     * Use either this method or {@link #setSession}, but not both.
     * <p>Non-default properties in this instance will override given
     * JavaMail properties.
     */
    public void setJavaMailProperties(Properties javaMailProperties) {
        this.javaMailProperties = javaMailProperties;
        synchronized (this) {
            this.session = null;
        }
    }

    /**
     * Allow Map access to the JavaMail properties of this sender,
     * with the option to add or override specific entries.
     * <p>Useful for specifying entries directly, for example via
     * "javaMailProperties[mail.smtp.auth]".
     */
    public Properties getJavaMailProperties() {
        return this.javaMailProperties;
    }

    /**
     * Set the JavaMail {@code Session}, possibly pulled from JNDI.
     * <p>Default is a new {@code Session} without defaults, that is
     * completely configured via this instance's properties.
     * <p>If using a pre-configured {@code Session}, non-default properties
     * in this instance will override the settings in the {@code Session}.
     * @see #setJavaMailProperties
     */
    public synchronized void setSession(Session session) {
        Assert.notNull(session, "Session must not be null");
        this.session = session;
    }

    /**
     * Return the JavaMail {@code Session},
     * lazily initializing it if hasn't been specified explicitly.
     */
    public synchronized Session getSession() {
        if (this.session == null) {
            this.session = Session.getInstance(this.javaMailProperties);
        }
        return this.session;
    }

    /**
     * Set the mail protocol. Default is "smtp".
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * Return the mail protocol.
     */
    public String getProtocol() {
        return this.protocol;
    }

    /**
     * Set the mail server host, typically an SMTP host.
     * <p>Default is the default host of the underlying JavaMail Session.
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Return the mail server host.
     */
    public String getHost() {
        return this.host;
    }

    /**
     * Set the mail server port.
     * <p>Default is {@link #DEFAULT_PORT}, letting JavaMail use the default
     * SMTP port (25).
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Return the mail server port.
     */
    public int getPort() {
        return this.port;
    }

    /**
     * Set the username for the account at the mail host, if any.
     * <p>Note that the underlying JavaMail {@code Session} has to be
     * configured with the property {@code "mail.smtp.auth"} set to
     * {@code true}, else the specified username will not be sent to the
     * mail server by the JavaMail runtime. If you are not explicitly passing
     * in a {@code Session} to use, simply specify this setting via
     * {@link #setJavaMailProperties}.
     * @see #setSession
     * @see #setPassword
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Return the username for the account at the mail host.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Set the password for the account at the mail host, if any.
     * <p>Note that the underlying JavaMail {@code Session} has to be
     * configured with the property {@code "mail.smtp.auth"} set to
     * {@code true}, else the specified password will not be sent to the
     * mail server by the JavaMail runtime. If you are not explicitly passing
     * in a {@code Session} to use, simply specify this setting via
     * {@link #setJavaMailProperties}.
     * @see #setSession
     * @see #setUsername
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Return the password for the account at the mail host.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Set the default encoding to use for {@link MimeMessage MimeMessages}
     * created by this instance.
     * <p>Such an encoding will be auto-detected by {@link MimeMessageHelper}.
     */
    public void setDefaultEncoding(String defaultEncoding) {
        this.defaultEncoding = defaultEncoding;
    }

    /**
     * Return the default encoding for {@link MimeMessage MimeMessages},
     * or {@code null} if none.
     */
    public String getDefaultEncoding() {
        return this.defaultEncoding;
    }

    /**
     * Set the default Java Activation {@link FileTypeMap} to use for
     * {@link MimeMessage MimeMessages} created by this instance.
     * <p>A {@code FileTypeMap} specified here will be autodetected by
     * {@link MimeMessageHelper}, avoiding the need to specify the
     * {@code FileTypeMap} for each {@code MimeMessageHelper} instance.
     * <p>For example, you can specify a custom instance of Spring's
     * {@link ConfigurableMimeFileTypeMap} here. If not explicitly specified,
     * a default {@code ConfigurableMimeFileTypeMap} will be used, containing
     * an extended set of MIME type mappings (as defined by the
     * {@code mime.types} file contained in the Spring jar).
     * @see MimeMessageHelper#setFileTypeMap
     */
    public void setDefaultFileTypeMap(FileTypeMap defaultFileTypeMap) {
        this.defaultFileTypeMap = defaultFileTypeMap;
    }

    /**
     * Return the default Java Activation {@link FileTypeMap} for
     * {@link MimeMessage MimeMessages}, or {@code null} if none.
     */
    public FileTypeMap getDefaultFileTypeMap() {
        return this.defaultFileTypeMap;
    }


    //---------------------------------------------------------------------
    // Implementation of MailSender
    //---------------------------------------------------------------------

    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException {
        send(new SimpleMailMessage[] {simpleMessage});
    }

    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException {
        List<MimeMessage> mimeMessages = new ArrayList<MimeMessage>(simpleMessages.length);
        for (SimpleMailMessage simpleMessage : simpleMessages) {
            MimeMailMessage message = new MimeMailMessage(createMimeMessage());
            simpleMessage.copyTo(message);
            mimeMessages.add(message.getMimeMessage());
        }
        doSend(mimeMessages.toArray(new MimeMessage[mimeMessages.size()]), simpleMessages);
    }


    //---------------------------------------------------------------------
    // Implementation of JavaMailSender
    //---------------------------------------------------------------------

    /**
     * This implementation creates a SmartMimeMessage, holding the specified
     * default encoding and default FileTypeMap. This special defaults-carrying
     * message will be autodetected by {@link MimeMessageHelper}, which will use
     * the carried encoding and FileTypeMap unless explicitly overridden.
     * @see #setDefaultEncoding
     * @see #setDefaultFileTypeMap
     */
    @Override
    public MimeMessage createMimeMessage() {
        return new SmartMimeMessage(getSession(), getDefaultEncoding(), getDefaultFileTypeMap());
    }

    @Override
    public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
        try {
            return new MimeMessage(getSession(), contentStream);
        }
        catch (Exception ex) {
            throw new MailParseException("Could not parse raw MIME content", ex);
        }
    }

    @Override
    public void send(MimeMessage mimeMessage) throws MailException {
        send(new MimeMessage[] {mimeMessage});
    }

    @Override
    public void send(MimeMessage... mimeMessages) throws MailException {
        doSend(mimeMessages, null);
    }

    @Override
    public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {
        send(new MimeMessagePreparator[] {mimeMessagePreparator});
    }

    @Override
    public void send(MimeMessagePreparator... mimeMessagePreparators) throws MailException {
        try {
            List<MimeMessage> mimeMessages = new ArrayList<MimeMessage>(mimeMessagePreparators.length);
            for (MimeMessagePreparator preparator : mimeMessagePreparators) {
                MimeMessage mimeMessage = createMimeMessage();
                preparator.prepare(mimeMessage);
                mimeMessages.add(mimeMessage);
            }
            send(mimeMessages.toArray(new MimeMessage[mimeMessages.size()]));
        }
        catch (MailException ex) {
            throw ex;
        }
        catch (MessagingException ex) {
            throw new MailParseException(ex);
        }
        catch (Exception ex) {
            throw new MailPreparationException(ex);
        }
    }

    /**
     * Validate that this instance can connect to the server that it is configured
     * for. Throws a {@link MessagingException} if the connection attempt failed.
     */
    public void testConnection() throws MessagingException {
        Transport transport = null;
        try {
            transport = connectTransport();
        }
        finally {
            if (transport != null) {
                transport.close();
            }
        }
    }

    /**
     * Actually send the given array of MimeMessages via JavaMail.
     * @param mimeMessages MimeMessage objects to send
     * @param originalMessages corresponding original message objects
     * that the MimeMessages have been created from (with same array
     * length and indices as the "mimeMessages" array), if any
     */
    protected void doSend(MimeMessage[] mimeMessages, Object[] originalMessages) throws MailException {
        Map<Object, Exception> failedMessages = new LinkedHashMap<Object, Exception>();
        Transport transport = null;

        try {
            for (int i = 0; i < mimeMessages.length; i++) {

                // Check transport connection first...
                if (transport == null || !transport.isConnected()) {
                    if (transport != null) {
                        try {
                            transport.close();
                        }
                        catch (Exception ex) {
                            // Ignore - we're reconnecting anyway
                        }
                        transport = null;
                    }
                    try {
                        transport = connectTransport();
                    }
                    catch (AuthenticationFailedException ex) {
                        throw new MailAuthenticationException(ex);
                    }
                    catch (Exception ex) {
                        // Effectively, all remaining messages failed...
                        for (int j = i; j < mimeMessages.length; j++) {
                            Object original = (originalMessages != null ? originalMessages[j] : mimeMessages[j]);
                            failedMessages.put(original, ex);
                        }
                        throw new MailSendException("Mail server connection failed", ex, failedMessages);
                    }
                }

                // Send message via current transport...
                MimeMessage mimeMessage = mimeMessages[i];
                try {
                    if (mimeMessage.getSentDate() == null) {
                        mimeMessage.setSentDate(new Date());
                    }
                    String messageId = mimeMessage.getMessageID();
                    mimeMessage.saveChanges();
                    if (messageId != null) {
                        // Preserve explicitly specified message id...
                        mimeMessage.setHeader(HEADER_MESSAGE_ID, messageId);
                    }
                    transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
                }
                catch (Exception ex) {
                    Object original = (originalMessages != null ? originalMessages[i] : mimeMessage);
                    failedMessages.put(original, ex);
                }
            }
        }
        finally {
            try {
                if (transport != null) {
                    transport.close();
                }
            }
            catch (Exception ex) {
                if (!failedMessages.isEmpty()) {
                    throw new MailSendException("Failed to close server connection after message failures", ex,
                            failedMessages);
                }
                else {
                    throw new MailSendException("Failed to close server connection after message sending", ex);
                }
            }
        }

        if (!failedMessages.isEmpty()) {
            throw new MailSendException(failedMessages);
        }
    }

    /**
     * Obtain and connect a Transport from the underlying JavaMail Session,
     * passing in the specified host, port, username, and password.
     * @return the connected Transport object
     * @throws MessagingException if the connect attempt failed
     * @since 4.1.2
     * @see #getTransport
     * @see #getHost()
     * @see #getPort()
     * @see #getUsername()
     * @see #getPassword()
     */
    protected Transport connectTransport() throws MessagingException {
        String username = getUsername();
        String password = getPassword();
        if ("".equals(username)) {  // probably from a placeholder
            username = null;
            if ("".equals(password)) {  // in conjunction with "" username, this means no password to use
                password = null;
            }
        }

        Transport transport = getTransport(getSession());
        transport.connect(getHost(), getPort(), username, password);
        return transport;
    }

    /**
     * Obtain a Transport object from the given JavaMail Session,
     * using the configured protocol.
     * <p>Can be overridden in subclasses, e.g. to return a mock Transport object.
     * @see javax.mail.Session#getTransport(String)
     * @see #getSession()
     * @see #getProtocol()
     */
    protected Transport getTransport(Session session) throws NoSuchProviderException {
        String protocol	= getProtocol();
        if (protocol == null) {
            protocol = session.getProperty("mail.transport.protocol");
            if (protocol == null) {
                protocol = DEFAULT_PROTOCOL;
            }
        }
        return session.getTransport(protocol);
    }

}

