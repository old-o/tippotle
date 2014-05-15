package net.doepner.mail;

import net.doepner.log.Log;
import net.doepner.log.LogProvider;

/**
 * Sends no emails, just logs warnings
 * (use it when email setup failed)
 */
public class NoEmailer implements Emailer {

    private final Log log;

    public NoEmailer(LogProvider logProvider) {
        log = logProvider.getLog(getClass());
    }

    @Override
    public void send(String text, String recipient, String subject) {
        log.$(Log.Level.warn, "No emailer active");
    }
}
