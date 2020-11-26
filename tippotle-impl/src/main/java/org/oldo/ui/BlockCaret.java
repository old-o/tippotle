package org.oldo.ui;

import javax.swing.text.DefaultCaret;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

/**
 * A caret that paints itself as a filled rectangle
 * on the bounding box of the next character
 */
public final class BlockCaret extends DefaultCaret {

    private final Color colorMask;
    private final CaretContext context;

    public BlockCaret(CaretContext context, Color colorMask) {
        this.context = context;
        this.colorMask = colorMask;
    }

    @Override
    public void paint(Graphics g) {
        final Graphics2D g2d = (Graphics2D) g;
        final AffineTransform old = g2d.getTransform();
        final int w = context.getCaretWidth();
        g.setXORMode(colorMask);
        g.translate(-w / 2, 0);
        super.paint(g);
        g2d.setTransform(old);
    }

    @Override
    protected synchronized void damage(Rectangle r) {
        if (r != null) {
            final int caretWidth = context.getCaretWidth();
            setBounds(r.x - caretWidth, r.y, caretWidth + 1, r.height);
            repaint();
        }
    }
}
