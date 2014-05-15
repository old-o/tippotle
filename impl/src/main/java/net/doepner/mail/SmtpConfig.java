package net.doepner.mail;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
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
}
