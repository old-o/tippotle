package net.doepner.app.typepad.action;

import net.doepner.mail.Emailer;
import net.doepner.speech.Speaker;
import net.doepner.text.TextProvider;
import net.doepner.ui.ActionId;
import net.doepner.ui.EmailDialog;
import net.doepner.ui.IAction;

/**
 * Sends emails
 */
public final class EmailAction implements IAction {

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
            emailer.send(recipient, emailDialog.subject(), textProvider.getText());
            speaker.speak("Email was sent to " + recipient);
        }
    }

    @Override
    public ActionId id() {
        return ActionEnum.EMAIL;
    }
}
