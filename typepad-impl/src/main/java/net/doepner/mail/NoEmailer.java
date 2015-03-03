package net.doepner.mail;

import org.guppy4j.log.Log;
import org.guppy4j.log.LogProvider;

import static org.guppy4j.log.Log.Level.warn;

/**
 * Sends no emails, just logs warnings
 * (use it when email setup failed)
 */
public final class NoEmailer implements Emailer {

    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    private final Log log;

    public NoEmailer(LogProvider logProvider) {
        log = logProvider.getLog(getClass());
    }

    @Override
    public void send(String recipient, String subject, String text) {
        log.as(warn, "Not sending email '{}' to {}. No emailer active!",
                subject, recipient);
    }
}
