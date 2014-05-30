package net.doepner.mail;

import net.doepner.log.Log;
import net.doepner.log.LogProvider;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import static net.doepner.log.Log.Level.error;

/**
 * Sends simple emails by SMTP
 */
public class SmtpEmailer implements Emailer {

    private final EmailConfig emailConfig;
    private final Log log;

    public SmtpEmailer(EmailConfig emailConfig, LogProvider logProvider) {
        this.emailConfig = emailConfig;
        this.log = logProvider.getLog(getClass());
    }

    @Override
    public void send(String text, String recipient, String subject) {

        final Session session = Session.getInstance(emailConfig.getProperties(), null);

        try {
            final MimeMessage msg = new MimeMessage(session);

            msg.setFrom(emailConfig.getSender());
            msg.setRecipients(Message.RecipientType.TO, recipient);
            msg.setSubject(subject);
            msg.setText(text);

            Transport.send(msg, emailConfig.getUsername(), emailConfig.getPassword());

        } catch (MessagingException e) {
            log.as(error, "Sending email failed", e);
        }
    }
}
