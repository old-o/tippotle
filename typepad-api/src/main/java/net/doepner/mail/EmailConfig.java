package net.doepner.mail;

import java.util.Properties;

/**
 * Email configuration
 */
public interface EmailConfig {

    Properties getProperties();

    String getSender();

    String getUsername();

    String getPassword();

    String[] getRecipientNames();

    String getEmailAddress(String recipient);
}
