package net.doepner.typepad;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

import net.doepner.ui.action.Action;
import net.doepner.ui.text.FontChooser;

public class TypePad {
	
	private final JFrame frame = new JFrame("TypePad");
	
	TypePad(final JTextPane pane, Iterable<Action> actions) {

		pane.setFont(new Font("Monospaced", Font.PLAIN, 40));
		pane.setPreferredSize(new Dimension(800, 600));

        final JToolBar toolBar = new JToolBar();
        for (Action action : actions) {
            ActionUtil.setIcon(action);
            toolBar.add(new JButton(action));
        }
        toolBar.add(new FontChooser(pane));

        ActionUtil.mapFunctionKeys(pane, actions);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(toolBar, BorderLayout.PAGE_START);
		frame.add(pane, BorderLayout.CENTER);
	}

    public void show() {
		frame.pack();
		frame.setVisible(true);
	}
}
