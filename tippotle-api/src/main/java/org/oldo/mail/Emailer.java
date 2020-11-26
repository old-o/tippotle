package org.oldo.mail;

/**
 * Sends plain-text emails
 */
public interface Emailer {

    void send(String recipient, String subject, String text);

}
