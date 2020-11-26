package org.oldo.ui;

import org.guppy4j.event.ChangeListener;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.text.JTextComponent;
import java.awt.Color;
import java.awt.Font;

import static java.awt.event.KeyEvent.VK_F1;
import static javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW;
import static javax.swing.KeyStroke.getKeyStroke;

/**
 * Swing based text editor component
 */
public final class SwingEditor implements Editor {

    private static final int NUMBER_OF_FUNCTION_KEYS = 12;

    private final JTextComponent component;

    public SwingEditor(JTextComponent component,
                       Color caretColorMask) {
        this.component = component;
        // disable the block caret for now since it is buggy:
//        component.setCaret(new BlockCaret(new BlockCaretContext(component), caretColorMask));
    }

    @Override
    public void addAction(Action action) {
        final InputMap inputMap = component.getInputMap(WHEN_IN_FOCUSED_WINDOW);
        final ActionMap actionMap = component.getActionMap();
        final int i = actionMap.size();
        if (i < NUMBER_OF_FUNCTION_KEYS) {
            inputMap.put(getKeyStroke(VK_F1 + i, 0), i);
            action.putValue(Action.NAME, "F" + (i + 1));
            actionMap.put(i, action);
        } else {
            throw new IllegalStateException("No more function key available for: " + action);
        }
    }

    private int lastCaretPosition = 0;

    @Override
    public void addTextPositionListener(final ChangeListener<Integer> tpl) {
        component.addCaretListener(e -> {
            final int caretPosition = e.getDot();
            tpl.handleChange(lastCaretPosition, caretPosition);
            lastCaretPosition = caretPosition;
        });
    }

    @Override
    public int textPosition() {
        return component.getCaretPosition();
    }

    @Override
    public void resizeFont(int step) {
        final Font f = component.getFont();
        final int newSize = f.getSize() + step;
        if (newSize > 0) {
            component.setFont(f.deriveFont((float) newSize));
        }
    }
}
