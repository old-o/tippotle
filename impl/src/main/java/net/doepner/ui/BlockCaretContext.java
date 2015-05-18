package net.doepner.ui;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.JTextComponent;
import java.awt.FontMetrics;
import java.io.Serializable;

/**
 * Manages and exposes the caret width for a text component
 * as the width of the bounding box of the next character in
 * document of the text component, relative to the current
 * caret position
 */
public final class BlockCaretContext implements CaretContext, Serializable {

    private static final long serialVersionUID = 1L;

    private static final String CARET_WIDTH_PROPERTY = "caretWidth";

    private final JTextComponent component;

    public BlockCaretContext(final JTextComponent component) {
        if (component == null) {
            throw new IllegalArgumentException("component must not be null");
        }
        this.component = component;
        this.component.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                updateCaretWidth();
            }
        });
        updateCaretWidth();
    }

    private void updateCaretWidth() {
        final Integer width = getWidth(component.getCaretPosition());
        component.putClientProperty(CARET_WIDTH_PROPERTY, width);
    }

    @Override
    public int getCaretWidth() {
        final Object caretWidth = component.getClientProperty(CARET_WIDTH_PROPERTY);
        return caretWidth instanceof Integer ? ((Integer) caretWidth).intValue() : 3;
    }

    private Integer getWidth(int pos) {
        final String text = component.getText();
        if (pos > 0 && pos <= text.length()) {
            final String currentCharacter = String.valueOf(text.charAt(pos - 1));
            final FontMetrics metrics = component.getFontMetrics(component.getFont());
            return metrics.stringWidth(currentCharacter);
        } else {
            return null;
        }
    }
}
