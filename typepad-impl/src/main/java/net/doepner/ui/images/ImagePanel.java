package net.doepner.ui.images;

import net.doepner.ui.ImageContainer;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import static java.awt.RenderingHints.KEY_ALPHA_INTERPOLATION;
import static java.awt.RenderingHints.KEY_INTERPOLATION;
import static java.awt.RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY;
import static java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR;
import static java.lang.Float.valueOf;
import static java.lang.Math.max;

public final class ImagePanel extends JPanel implements ImageContainer {

    private Image image;

    public ImagePanel() {
        image = null;
    }

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
            final int imageWidth = image.getWidth(null);
            final int imageHeight = image.getHeight(null);

            final int width = getWidth();
            final int heigth = getHeight();

            final float xScale = imageWidth > width ? (float) imageWidth / (float) width : 1.0f;
            final float yScale = imageHeight > heigth ? (float) imageHeight / (float) heigth : 1.0f;

            final float scale = max(xScale, yScale);

            final int targetWidth = valueOf((float) imageWidth / scale).intValue();
            final int targetHeight = valueOf((float) imageHeight / scale).intValue();

            final int widthMargin = (width - targetWidth) / 2;
            final int heightMargin = (heigth - targetHeight) / 2;

            final Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(KEY_INTERPOLATION, VALUE_INTERPOLATION_BILINEAR);
            g2.setRenderingHint(KEY_ALPHA_INTERPOLATION, VALUE_ALPHA_INTERPOLATION_QUALITY);

            g.drawImage(image, widthMargin, heightMargin, targetWidth, targetHeight, null);
        }
    }
}
