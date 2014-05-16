package net.doepner.ui;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.JTextComponent;
import java.awt.FontMetrics;

/**
 * Manages and exposes the caret width for a text component
 * as the width of the bounding box of the next character in
 * document of the text component, relative to the current
 * caret position
 */
public class SwingCaretContext implements CaretContext {

    private final JTextComponent component;

    public SwingCaretContext(final JTextComponent component) {
        if (component == null) {
            throw new IllegalArgumentException("component must not be null");
        }
        this.component = component;
        this.component.addCaretListener(new CaretListener() {
            public void caretUpdate(CaretEvent e) {
                updateCaretWidth();
            }
        });
        updateCaretWidth();
    }

    private void updateCaretWidth() {
        final Integer width = getWidth(component.getCaretPosition());
        component.putClientProperty("caretWidth", width);
    }

    @Override
    public int getCaretWidth() {
        final Object caretWidth = component.getClientProperty("caretWidth");
        return caretWidth instanceof Integer ? (int) caretWidth : 3;
    }

    private Integer getWidth(int pos) {
        if (pos > 0) {
            final String currentCharacter = String.valueOf(component.getText().charAt(pos - 1));
            final FontMetrics fontMetrics = component.getFontMetrics(component.getFont());
            return fontMetrics.stringWidth(currentCharacter);
        } else {
            return null;
        }
    }
}
