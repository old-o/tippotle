package net.doepner.app.typepad.action;

import net.doepner.mail.Emailer;
import net.doepner.speech.Speaker;
import net.doepner.text.TextProvider;
import net.doepner.ui.Action;
import net.doepner.ui.EmailDialog;

/**
 * Sends emails
 */
public final class EmailAction implements Action {

    private final EmailDialog emailDialog;
    private final TextProvider textProvider;

    private final Emailer emailer;
    private final Speaker speaker;

    public EmailAction(EmailDialog emailDialog,
                       TextProvider textProvider,
                       Emailer emailer,
                       Speaker speaker) {
        this.emailDialog = emailDialog;
        this.textProvider = textProvider;
        this.emailer = emailer;
        this.speaker = speaker;
    }

    @Override
    public void execute() {
        final String recipient = emailDialog.recipient();

        if (recipient != null) {
            try {
                emailer.send(recipient, emailDialog.subject(), textProvider.getText());
                speaker.speak("Email was sent to " + recipient);
            } catch (IllegalStateException e) {
                speaker.speak("Sending of email failed");
            }
        }
    }

    @Override
    public ActionEnum id() {
        return ActionEnum.EMAIL;
    }
}
