package net.doepner.ui;

import org.guppy4j.event.ChangeListener;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.text.JTextComponent;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

import static javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW;
import static javax.swing.KeyStroke.getKeyStroke;

/**
 * Swing based text editor component
 */
public final class SwingEditor implements Editor {

    private final JTextComponent editor;

    public SwingEditor(JTextComponent editor,
                       Color caretColorMask) {
        this.editor = editor;
        editor.setCaret(new BlockCaret(new BlockCaretContext(editor), caretColorMask));
    }

    @Override
    public void addAction(Action action) {
        final InputMap inputMap = editor.getInputMap(WHEN_IN_FOCUSED_WINDOW);
        final ActionMap actionMap = editor.getActionMap();
        final int i = actionMap.size();
        inputMap.put(getKeyStroke(KeyEvent.VK_F1 + i, 0), i);
        action.putValue(Action.NAME, "F" + (i + 1));
        actionMap.put(i, action);
    }

    @Override
    public void addTextPositionListener(final ChangeListener<Integer> tpl) {
        editor.addCaretListener(e -> tpl.handleChange(null, e.getDot()));
    }

    @Override
    public int textPosition() {
        return editor.getCaretPosition();
    }

    @Override
    public void resizeFont(int step) {
        final Font f = editor.getFont();
        final int newSize = f.getSize() + step;
        if (newSize > 0) {
            editor.setFont(f.deriveFont((float) newSize));
        }
    }
}
