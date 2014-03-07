package net.doepner.ui;

import java.awt.Rectangle;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;

/**
 * Manages and exposes the caret width for a text component
 * as the width of the bounding box of the next character in
 * document of the text component, relative to the current
 * caret position
 */
public class SwingCaretContext implements CaretContext {

    private static final Rectangle NULL_RECTANGLE = new Rectangle();

    private final JTextComponent component;

    public SwingCaretContext(final JTextComponent component) {
        if (component == null) {
            throw new IllegalArgumentException("component must not be null");
        }
        component.addCaretListener(new CaretListener() {
            public void caretUpdate(CaretEvent e) {
                component.putClientProperty("caretWidth",
                    getWidth(component.getCaretPosition()));
            }
        });
        this.component = component;
    }

    @Override
    public int getCaretWidth() {
        final Object caretWidth = component.getClientProperty("caretWidth");
        return caretWidth instanceof Integer ? (int) caretWidth : 1;
    }

    private int getWidth(int pos) {
        if (pos > 0) {
            final Rectangle prevBox = getBoundingBox(pos - 1);
            final Rectangle box = getBoundingBox(pos);
            if (prevBox.y == box.y) {
                final int width = box.x - prevBox.x;
                if (width > 0) {
                    return width + 1;
                }
            }
        }
        // otherwise
        return 1;
    }

    private Rectangle getBoundingBox(int pos) {
        try {
            final Rectangle r1 = component.modelToView(pos);
            if (r1 != null) {
                return r1.getBounds();
            }
        } catch (BadLocationException e) {
            // ignore
        }
        return NULL_RECTANGLE;
    }
}
