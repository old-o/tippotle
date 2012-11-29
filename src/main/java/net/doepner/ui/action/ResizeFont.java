package net.doepner.ui.action;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class ResizeFont extends AbstractAction implements IdAction {

	private final int step;
	private final Component component;

	public ResizeFont(int step, Component component) {
		this.step = step;
		this.component = component;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		final Font f = component.getFont();
		final int newSize = f.getSize() + step;
		if (newSize > 0) {
			component.setFont(f.deriveFont((float) newSize));
		}
	}

    @Override
    public ActionId getId() {
        return step > 0 ? ActionId.BIGGER_FONT : ActionId.SMALLER_FONT;
    }
}
