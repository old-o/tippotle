package net.doepner.ui.images;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class ImagePanel extends JPanel implements ImageContainer {

    private Image image;

    @Override
    public void setImage(Image image) {
        if (this.image != image) {
            this.image = image;
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null) {
            final Image img = image.getScaledInstance(
                    getWidth(), getHeight(), Image.SCALE_SMOOTH);
            g.drawImage(img, 0, 0, null);
        }
    }
}
