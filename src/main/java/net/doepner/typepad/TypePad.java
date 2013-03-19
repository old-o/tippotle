package net.doepner.typepad;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

import net.doepner.ui.ImagePanel;
import net.doepner.ui.Showable;
import net.doepner.ui.text.FontChooser;

import static javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW;
import static javax.swing.KeyStroke.getKeyStroke;

public class TypePad implements Showable {
	
	private final JFrame frame = new JFrame("TypePad");
    private final ImagePanel imageContainer = new ImagePanel();

    TypePad(final JTextPane pane, Iterable<Action> actions) {

		pane.setFont(new Font("Monospaced", Font.PLAIN, 40));
		pane.setPreferredSize(new Dimension(800, 600));

        final JToolBar toolBar = new JToolBar();
        addActions(pane, actions, toolBar);
        toolBar.add(new FontChooser(pane));
        toolBar.setFloatable(false);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(toolBar, BorderLayout.PAGE_START);

        final JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(new JScrollPane(pane), BorderLayout.CENTER);
        final JPanel statusBar = new JPanel();
        wrapper.add(statusBar, BorderLayout.SOUTH);
        statusBar.add(imageContainer);

        imageContainer.setPreferredSize(new Dimension(120, 120));

        frame.add(wrapper, BorderLayout.CENTER);
	}

    private void addActions(JTextPane pane, Iterable<Action> actions,
                            JToolBar toolBar) {

        final InputMap inputMap = pane.getInputMap(WHEN_IN_FOCUSED_WINDOW);
        final ActionMap actionMap = pane.getActionMap();

        int i = 0;
        for (Action action : actions) {
            inputMap.put(getKeyStroke(KeyEvent.VK_F1 + i, 0), i);
            action.putValue(Action.NAME, "F" + (i+1));
            actionMap.put(i, action);
            i++;
            toolBar.add(new JButton(action));
        }
    }

    @Override
    public void show() {
		frame.pack();
		frame.setVisible(true);
	}

    @Override
    public void showImage(Image image) {
        imageContainer.setImage(image);
    }
}
