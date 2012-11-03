package net.doepner.typepad;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

import net.doepner.ui.text.FontChooser;

public class TypePad {
	
	private final JFrame frame = new JFrame("TypePad");
	
	public TypePad(final JTextPane pane, Action... actions) {

		pane.setFont(new Font("Monospaced", Font.PLAIN, 40));
		pane.setPreferredSize(new Dimension(800, 600));
		
		final JToolBar toolBar = new JToolBar();		
		addActions(pane, toolBar, actions);		
		toolBar.add(new FontChooser(pane));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(toolBar, BorderLayout.PAGE_START);
		frame.add(pane, BorderLayout.CENTER);
	}

	private static void addActions(JTextPane pane, JToolBar toolBar, Action[] actions) {
		final InputMap inputMap = pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		for (int i = 0; i < actions.length; i++) {
			final String id = Integer.toString(i);
			inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1 + i, 0), id);
			
			final Action action = actions[i];
			action.putValue(Action.NAME, "F" + (i+1));
			pane.getActionMap().put(id, action);
			toolBar.add(new JButton(action));
		}
	}

	public void show() {
		frame.pack();
		frame.setVisible(true);
	}
}
