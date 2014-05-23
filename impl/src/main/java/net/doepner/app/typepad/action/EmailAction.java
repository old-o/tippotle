package net.doepner.app.typepad.action;

import net.doepner.app.typepad.IModel;
import net.doepner.app.typepad.IServices;
import net.doepner.mail.Emailer;
import net.doepner.ui.ActionId;
import net.doepner.ui.EmailDialog;
import net.doepner.ui.IAction;

/**
 * Sends emails
 */
public class EmailAction implements IAction {

    private final EmailDialog emailDialog;
    private final IModel model;
    private Emailer emailer;

    public EmailAction(EmailDialog emailDialog, IModel model, IServices services) {
        this.emailDialog = emailDialog;
        this.model = model;
        emailer = services.getEmailer();
    }

    @Override
    public void actionPerformed() {
        final String recipient = emailDialog.getRecipient();
        if (recipient != null) {
            emailer.send(model.getText(), recipient, emailDialog.getSubject());
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
