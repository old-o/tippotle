package net.doepner.ui.text;

import javax.swing.text.JTextComponent;

import net.doepner.text.TextCoordinates;


public class TextUiInfo implements TextCoordinates {
	
	private final JTextComponent component; 

	public TextUiInfo(JTextComponent component) {
		this.component = component;
	}

	@Override
	public int getOffset() {
		return component.getCaretPosition();
	}
}
