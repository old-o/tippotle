package net.doepner.ui.text;

import javax.swing.text.JTextComponent;

import net.doepner.text.TextModel;

public class UiTextModel implements TextModel {

	final JTextComponent comp;
	
	public UiTextModel(JTextComponent comp) {
		this.comp = comp;
	}

	@Override
	public void setText(String text) {
		comp.setText(text);
		comp.setCaretPosition(0);
	}

	@Override
	public String getText() {
		return comp.getText();
	}
}
