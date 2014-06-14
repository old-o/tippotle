package net.doepner.ui;

import net.doepner.resources.ResourceFinder;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import static net.doepner.file.MediaTypeEnum.image;

/**
 * Prompts user to select an email recipient
 */
public class SwingEmailDialog implements EmailDialog {

    private final ResourceFinder finder;

    public SwingEmailDialog(ResourceFinder finder) {
        this.finder = finder;
    }

    @Override
    public String chooseRecipient(String[] recipients) {

        final Icon[] options = new Icon[recipients.length];
        for (int i = 0; i < options.length; i++) {
            options[i] = getIcon(recipients[i]);
        }

        final int choice = JOptionPane.showOptionDialog(
                null, "Recipient?", "Send email ...",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                options, options[0]);

        return choice == JOptionPane.CLOSED_OPTION ? null : recipients[choice];
    }

    private Icon getIcon(String name) {
        return new ImageIcon(finder.find(image, name, null, "email"));
    }

    @Override
    public String getSubject() {
        // TODO: Make the subject configurable
        return "Typepad message";
    }

}
