package net.doepner.ui;

import net.doepner.mail.EmailConfig;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Image;
import java.util.Iterator;
import java.util.function.Function;

import static javax.swing.JOptionPane.CLOSED_OPTION;
import static javax.swing.JOptionPane.DEFAULT_OPTION;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showOptionDialog;

/**
 * Prompts user to select an email recipient
 */
public final class SwingEmailDialog implements EmailDialog {

    private static final String NO_CHOICE = null;

    private final Function<String, Iterable<Image>> imageCollector;
    private final EmailConfig config;

    private final JPanel inputPanel;
    private final JTextField subjectField;

    public SwingEmailDialog(Function<String, Iterable<Image>> imageCollector,
                            EmailConfig config) {
        this.imageCollector = imageCollector;
        this.config = config;

        subjectField = new JTextField("Tippotle message");

        inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(new JLabel("Subject: "), BorderLayout.WEST);
        inputPanel.add(subjectField, BorderLayout.CENTER);
    }

    @Override
    public String recipient() {
        final String[] recipients = config.recipientNames();
        if (recipients == null || recipients.length == 0) {
            showMessageDialog(null, "No email recipients configured", "Error", ERROR_MESSAGE);
            return NO_CHOICE;
        }

        final Icon[] options = new Icon[recipients.length];
        for (int i = 0; i < options.length; i++) {
            options[i] = createIcon(recipients[i]);
        }

        final int choice = showOptionDialog(null, inputPanel, "Send email ...",
                DEFAULT_OPTION, QUESTION_MESSAGE, null,
                options, options[0]);

        return choice == CLOSED_OPTION ? NO_CHOICE : recipients[choice];
    }

    private Icon createIcon(String name) {
        final Iterator<Image> images = imageCollector.apply(name).iterator();
        return images.hasNext() ? new ImageIcon(images.next()) : new ImageIcon();
    }

    @Override
    public String subject() {
        return subjectField.getText();
    }
}
