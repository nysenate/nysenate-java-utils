package gov.nysenate.util;

import java.util.Properties;
import java.util.StringTokenizer;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Can send mail.
 *
 * prefix.host =
 * prefix.debug = 0 (off) or 1 (on)
 * prefix.active = 0 (off) or 1 (on)
 * prefix.port =
 * prefix.user =
 * prefix.pass =
 * prefix.admin =
 *
 */
public class Mailer
{
    private static final Logger logger = LoggerFactory.getLogger(Mailer.class);
    private final Config config;
    private final String SMTP_HOST_NAME;
    private final String SMTP_DEBUG;
    private final String SMTP_ACTIVE;
    private final String SMTP_PORT;
    private final String SMTP_ACCOUNT_USER;
    private final String SMTP_ACCOUNT_PASS;
    private final String SMTP_ADMIN;
    private final String SMTP_TLS_ENABLE;
    private final String SMTP_SSL_ENABLE;
    private final String SMTP_CONTEXT;

    public Mailer(Config appConfig, String prefix)
    {

        this.config = appConfig;
        SMTP_HOST_NAME = config.getValue(prefix+".host");
        SMTP_DEBUG = config.getValue(prefix+".debug");
        SMTP_ACTIVE = config.getValue(prefix+".active");
        SMTP_PORT = config.getValue(prefix+".port");
        SMTP_ACCOUNT_USER = config.getValue(prefix+".user");
        SMTP_ACCOUNT_PASS = config.getValue(prefix+".pass");
        SMTP_ADMIN = config.getValue(prefix+".admin");
        SMTP_TLS_ENABLE = config.getValue(prefix+".tls.enable");
        SMTP_SSL_ENABLE = config.getValue(prefix+".ssl.enable");
        SMTP_CONTEXT = config.getValue(prefix+".context");
        logger.info("Setting up mailer for "+SMTP_ACCOUNT_USER+"@"+SMTP_HOST_NAME+":"+SMTP_PORT);
    }

    public String getContext() {
        return SMTP_CONTEXT;
    }

    public String getAdminEmail() {
        return SMTP_ADMIN;
    }

    public void sendMail(String to, String subject, String message) throws Exception
    {
        sendMail(to, subject, message, SMTP_ACCOUNT_USER, "SAGE");
    }

    public void sendMail(String to, String subject, String message, String from, String fromDisplay) throws Exception
    {
        if (!SMTP_ACTIVE.equals("true")) return;

        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", SMTP_DEBUG);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.starttls.enable",SMTP_TLS_ENABLE);
        props.put("mail.smtp.socketFactory.port", SMTP_PORT);
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.ssl.enable",SMTP_SSL_ENABLE);

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_ACCOUNT_USER, SMTP_ACCOUNT_PASS);
            }
        });

        Message msg = new MimeMessage(session);
        InternetAddress addressFrom = new InternetAddress(from);
        addressFrom.setPersonal(fromDisplay);
        msg.setFrom(addressFrom);

        StringTokenizer st = new StringTokenizer (to,",");
        InternetAddress[] rcps = new InternetAddress[st.countTokens()];
        int idx = 0;

        while (st.hasMoreTokens()) {
            InternetAddress addressTo = new InternetAddress(st.nextToken());
            rcps[idx++] = addressTo;
        }

        logger.debug("Recipients list: " + rcps);
        msg.setRecipients(Message.RecipientType.TO,rcps);
        msg.setSubject(subject);
        msg.setContent(message, "text/html");
        Transport.send(msg);
        logger.debug("Message delivered!");
    }
}
