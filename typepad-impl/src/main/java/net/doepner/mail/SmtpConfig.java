package net.doepner.mail;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * SMTP email configuration from properties file
 */
public class SmtpConfig implements EmailConfig {

    private final Properties props = new Properties();

    public SmtpConfig(Path propertiesFile) throws IOException {
        try (InputStream stream = Files.newInputStream(propertiesFile)) {
            props.load(stream);
        }
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
        final List<String> list = new ArrayList<>();
        for (String name : props.stringPropertyNames()) {
            if (name.startsWith("mail.recipient.")) {
                list.add(name.substring("mail.recipient.".length()));
            }
        }
        return list.toArray(new String[list.size()]);
    }

    @Override
    public String getEmailAddress(String recipient) {
        return props.getProperty("mail.recipient." + recipient);
    }
}
