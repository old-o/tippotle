package net.doepner.ui.action;

import static net.doepner.ui.Icons.Category.GENERAL;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.doepner.ui.Icons;

public class FontSizingAction extends AbstractAction {

	private final int step;
	private final Component component;

	public FontSizingAction(int step, Component component) {
		this.step = step;
		this.component = component;
		Icons.setLargeIcon(this, GENERAL, "Zoom" + (step > 0 ? "In" : "Out"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final Font f = component.getFont();
		final int newSize = f.getSize() + step;
		if (newSize > 0) {
			component.setFont(f.deriveFont((float) newSize));
		}
	}

}
