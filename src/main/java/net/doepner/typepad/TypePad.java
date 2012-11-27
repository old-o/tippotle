package net.doepner.typepad;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import net.doepner.ui.Showable;
import net.doepner.ui.action.Action;
import net.doepner.ui.text.FontChooser;

import static net.doepner.ui.icons.IconLoader.setIcon;

public class TypePad implements Showable {
	
	private final JFrame frame = new JFrame("TypePad");
	
	TypePad(final JTextPane pane, Iterable<? extends Action> actions) {

		pane.setFont(new Font("Monospaced", Font.PLAIN, 40));
		pane.setPreferredSize(new Dimension(800, 600));

        final JToolBar toolBar = new JToolBar();
        addActions(pane, actions, toolBar);
        toolBar.add(new FontChooser(pane));
        toolBar.setFloatable(false);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(toolBar, BorderLayout.PAGE_START);

        final JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(pane, BorderLayout.CENTER);
        final JPanel statusBar = new JPanel();
        wrapper.add(statusBar, BorderLayout.SOUTH);
        statusBar.add(new JTextField("blah"));

        frame.add(wrapper, BorderLayout.CENTER);
	}

    private void addActions(JTextPane pane, Iterable<? extends Action> actions,
                            JToolBar toolBar) {
        final InputMap inputMap =
                pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        Integer i = 0;
        for (Action action : actions) {
            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1 + i, 0), i);
            action.putValue(Action.NAME, "F" + (i+1));
            pane.getActionMap().put(i, action);
            i++;
            setIcon(action);
            toolBar.add(new JButton(action));
        }
    }

    @Override
    public void show() {
		frame.pack();
		frame.setVisible(true);
	}
}
