package net.doepner.ui;

import net.doepner.event.ChangeListener;
import net.doepner.ui.text.FontChooser;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.KeyEvent;

import static javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW;
import static javax.swing.KeyStroke.getKeyStroke;

/**
 * Swing based text editor component
 */
public class SwingEditor implements Editor {

    private final JTextPane editor;

    public SwingEditor(JTextPane editor) {
        this.editor = editor;
        editor.setCaret(new BlockCaret(new SwingCaretContext(editor)));
    }

    public void addAction(Action action, int i) {
        final InputMap inputMap = editor.getInputMap(WHEN_IN_FOCUSED_WINDOW);
        final ActionMap actionMap = editor.getActionMap();
        inputMap.put(getKeyStroke(KeyEvent.VK_F1 + i, 0), i);
        action.putValue(Action.NAME, "F" + (i + 1));
        actionMap.put(i, action);
    }

    @Override
    public void addTextPositionListener(final ChangeListener<Integer> tpl) {
        editor.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                tpl.handleChange(null, e.getDot());
            }
        });
    }

    @Override
    public int getTextPosition() {
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

    public Component createScrollPane() {
        return new JScrollPane(editor);
    }

    public Component createFontChooser() {
        return new FontChooser(editor);
    }
}
