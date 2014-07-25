package net.doepner.app.typepad.action;

import net.doepner.mail.Emailer;
import net.doepner.text.TextProvider;
import net.doepner.ui.ActionId;
import net.doepner.ui.EmailDialog;
import net.doepner.ui.IAction;

/**
 * Sends emails
 */
public class EmailAction implements IAction {

    private final EmailDialog emailDialog;
    private final TextProvider textProvider;
    private Emailer emailer;

    public EmailAction(EmailDialog emailDialog,
                       TextProvider textProvider,
                       Emailer emailer) {
        this.emailDialog = emailDialog;
        this.textProvider = textProvider;
        this.emailer = emailer;
    }

    @Override
    public void actionPerformed() {
        final String recipient = emailDialog.chooseRecipient(
                emailer.getAvailableRecipients());

        if (recipient != null) {
            emailer.send(recipient, emailDialog.getSubject(), textProvider.getText());
        }
    }

    @Override
    public String getIconName() {
        return getId().getIconName();
    }

    @Override
    public ActionId getId() {
        return ActionEnum.EMAIL;
    }
}
