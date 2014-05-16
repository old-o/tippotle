package net.doepner.ui;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.awt.Image;
import java.util.Iterator;

/**
 * Prompts user to select an email recipient
 */
public class SwingEmailDialog implements EmailDialog {

    private final SwingFrame frame;
    private final Images images;

    public SwingEmailDialog(SwingFrame frame, Images images) {
        this.frame = frame;
        this.images = images;
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
        final Iterator<Image> iterator = images.getImages(name).iterator();
        return iterator.hasNext() ? new ImageIcon(iterator.next())
                : new ImageIcon("http://oliver.doepner.net/pics/oliver.jpg");
    }

    @Override
    public String getSubject() {
        // TODO: Make the subject configurable
        return "Typepad message";
    }

}
