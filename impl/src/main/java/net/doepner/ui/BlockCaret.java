package net.doepner.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import javax.swing.text.DefaultCaret;

/**
 * A caret that paints itself as a filled rectangle
 * on the bounding box of the next character
 */
public class BlockCaret extends DefaultCaret {

    private static final Color XOR_MODE = new Color(0, 50, 50);

    private final CaretContext context;

    public BlockCaret(CaretContext context) {
        this.context = context;
    }

    public void paint(Graphics g) {
        final Graphics2D g2d = (Graphics2D) g;
        final AffineTransform old = g2d.getTransform();
        int w = context.getCaretWidth();
        g.setXORMode(XOR_MODE);
        g.translate(-w / 2, 0);
        super.paint(g);
        g2d.setTransform(old);
    }

    protected synchronized void damage(Rectangle r) {
        if (r != null) {
            int damageWidth = context.getCaretWidth();

            System.out.println("r = " + r.toString());
            System.out.println("bounds before= " + getBounds());
            System.out.println("damageWidth = " + damageWidth);

            // TODO: Fix this so that newly typed character appears in block

            x = r.x - damageWidth;
            y = r.y;
            width = damageWidth + 1;
            height = r.height;

            System.out.println("bounds after = " + getBounds());

            repaint();
        }
    }
}
