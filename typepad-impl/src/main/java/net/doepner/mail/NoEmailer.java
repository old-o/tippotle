package net.doepner.mail;

import net.doepner.log.Log;
import net.doepner.log.LogProvider;

import static net.doepner.log.Log.Level.warn;

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
        log.as(warn, "Not sending email '{}' to {}. No emailer active!",
                subject, recipient);
    }

    @Override
    public String[] getAvailableRecipients() {
        return new String[0];
    }
}
