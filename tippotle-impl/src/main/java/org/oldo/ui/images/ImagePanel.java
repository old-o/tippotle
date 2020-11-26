package org.oldo.ui.images;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;

import static java.awt.RenderingHints.KEY_ALPHA_INTERPOLATION;
import static java.awt.RenderingHints.KEY_INTERPOLATION;
import static java.awt.RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY;
import static java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR;
import static java.lang.Float.valueOf;
import static java.lang.Math.max;
import static org.guppy4j.BaseUtil.bothNullOrEqual;
import static org.guppy4j.BaseUtil.not;

public final class ImagePanel extends JPanel implements ImageContainer {

    private Image image;

    public ImagePanel(Dimension size) {
        image = null;
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        setPreferredSize(size);
        setMaximumSize(size);
    }

    @Override
    public void setImage(Image image) {
        if (not(bothNullOrEqual(this.image, image))) {
            this.image = image;
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            drawScaledImageToFit(this, image, g);
        }
    }

    private static void drawScaledImageToFit(JComponent comp, Image img, Graphics g) {
        setInterpolation(g);

        final Insets insets = comp.getInsets();

        final int imgWidth = img.getWidth(null);
        final int imgHeight = img.getHeight(null);

        final int w = withoutInsets(comp.getWidth(), insets.left, insets.right);
        final int h = withoutInsets(comp.getHeight(), insets.top, insets.bottom);

        final float scale = max(scale(imgWidth, w), scale(imgHeight, h));

        final int imgW = apply(imgWidth, scale);
        final int imgH = apply(imgHeight, scale);

        final int x = pos(insets.left, w, imgW);
        final int y = pos(insets.top, h, imgH);

        g.drawImage(img, x, y, imgW, imgH, null);
    }

    private static int withoutInsets(int total, int insetFromStart, int insetFromEnd) {
        return total - insetFromStart - insetFromEnd;
    }

    private static float scale(int original, int available) {
        return original > available ? (float) original / (float) available : 1.0f;
    }

    private static int apply(int imgExtent, float scale) {
        return valueOf((float) imgExtent / scale).intValue();
    }

    private static int pos(int inset, int available, int actual) {
        return inset + (available - actual) / 2;
    }

    private static void setInterpolation(Graphics g) {
        final Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(KEY_INTERPOLATION, VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(KEY_ALPHA_INTERPOLATION, VALUE_ALPHA_INTERPOLATION_QUALITY);
    }
}
