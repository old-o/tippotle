package net.doepner.mail;

/**
 * Sends plain-text emails
 */
public interface Emailer {

    void send(String text, String recipient, String subject);

}
