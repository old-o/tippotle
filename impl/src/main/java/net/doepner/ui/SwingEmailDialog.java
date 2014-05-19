package net.doepner.ui;

import java.net.URL;
import java.util.Iterator;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import net.doepner.app.typepad.ui.SwingFrame;
import net.doepner.resources.ResourceFinder;

import static net.doepner.file.MediaTypeEnum.image;

/**
 * Prompts user to select an email recipient
 */
public class SwingEmailDialog implements EmailDialog {

    private final SwingFrame frame;
    private final ResourceFinder finder;

    public SwingEmailDialog(SwingFrame frame, ResourceFinder finder) {
        this.frame = frame;
        this.finder = finder;
    }

    @Override
    public String getRecipient() {

        // TODO: Make the recipient list configurable

        final Icon[] options = {
                getImageIcon("oliver"),
                getImageIcon("heather"),
        };

        final String[] addresses = {
                "odoepner@gmail.com",
                "hldoepner@gmail.com"
        };

        final int choice = JOptionPane.showOptionDialog(
                frame.getMainComponent(), "Recipient?", "Send email ...",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                options, options[0]);

        return choice == JOptionPane.CLOSED_OPTION ? null : addresses[choice];
    }

    private ImageIcon getImageIcon(String name) {
        final Iterator<URL> iterator = finder.findAll(image, name).iterator();
        return iterator.hasNext() ? new ImageIcon(iterator.next())
                : new ImageIcon("http://oliver.doepner.net/pics/oliver.jpg");
    }

    @Override
    public String getSubject() {
        // TODO: Make the subject configurable
        return "Typepad message";
    }

}
