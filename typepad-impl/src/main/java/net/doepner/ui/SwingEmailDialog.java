package net.doepner.ui;

import net.doepner.resources.ResourceFinder;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import static javax.swing.JOptionPane.CLOSED_OPTION;
import static javax.swing.JOptionPane.DEFAULT_OPTION;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
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
            options[i] = createIcon(recipients[i]);
        }

        final int choice = JOptionPane.showOptionDialog(null,
                "Recipient?", "Send email ...",
                DEFAULT_OPTION, QUESTION_MESSAGE, null,
                options, options[0]);

        return choice == CLOSED_OPTION ? null : recipients[choice];
    }

    private Icon createIcon(String name) {
        return new ImageIcon(finder.find(image, name, null, "email"));
    }

    @Override
    public String getSubject() {
        // TODO: Make the subject configurable
        return "Typepad message";
    }

}
