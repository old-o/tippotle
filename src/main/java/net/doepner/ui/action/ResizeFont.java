package net.doepner.ui.action;

import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.doepner.ui.HasFont;

public class ResizeFont extends AbstractAction implements IdAction {

	private final int step;
	private final HasFont hasFont;

	public ResizeFont(int step, HasFont hasFont) {
		this.step = step;
		this.hasFont = hasFont;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		final Font f = hasFont.getFont();
		final int newSize = f.getSize() + step;
		if (newSize > 0) {
			hasFont.setFont(f.deriveFont((float) newSize));
		}
	}

    @Override
    public ActionId getId() {
        return step > 0 ? ActionId.BIGGER_FONT : ActionId.SMALLER_FONT;
    }
}
