package net.doepner.mail;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * SMTP email configuration from properties file
 */
public class SmtpConfig implements EmailConfig {

    private final Properties props;

    private final String recipientPropertyPrefix = "mail.recipient.";
    private String[] recipientNames;

    public SmtpConfig(Path propertiesFile) throws IOException {
        final Properties props = new Properties();
        try (InputStream stream = Files.newInputStream(propertiesFile)) {
            props.load(stream);
        }
        recipientNames = getRecipientNames(props, recipientPropertyPrefix);
        this.props = props;
    }

    @Override
    public String getSender() {
        return props.getProperty("mail.from");
    }

    @Override
    public Properties getProperties() {
        return props;
    }

    @Override
    public String getUsername() {
        return props.getProperty("mail.smtp.user");
    }

    @Override
    public String getPassword() {
        return props.getProperty("mail.smtp.password");
    }

    @Override
    public String[] getRecipientNames() {
        return recipientNames;
    }

    private static String[] getRecipientNames(Properties props,
                                              String propertyPrefix) {
        final List<String> list = new LinkedList<>();
        for (String name : props.stringPropertyNames()) {
            if (name.startsWith(propertyPrefix)) {
                list.add(name.substring(propertyPrefix.length()));
            }
        }
        return list.toArray(new String[list.size()]);
    }

    @Override
    public String getEmailAddress(String recipient) {
        return props.getProperty(recipientPropertyPrefix + recipient);
    }
}
