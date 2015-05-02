package net.doepner.mail;

import java.util.Properties;

/**
 * Email configuration
 */
public interface EmailConfig {

    Properties properties();

    String sender();

    String username();

    String password();

    String[] recipientNames();

    String emailAddress(String recipient);
}
