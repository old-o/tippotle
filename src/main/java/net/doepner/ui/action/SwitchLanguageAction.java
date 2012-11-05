package net.doepner.ui.action;

import java.awt.event.ActionEvent;

import javax.swing.*;

import net.doepner.lang.LanguageChanger;
import net.doepner.ui.Icons;

public class SwitchLanguageAction extends AbstractAction {

	private final LanguageChanger langChanger;
	
	public SwitchLanguageAction(LanguageChanger langChanger) {
		this.langChanger = langChanger;
        putValue(SHORT_DESCRIPTION, "Switch language");
		update();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		langChanger.changeLanguage();
		update();
	}

	private void update() {
		final String langCode = langChanger.getLanguage().getCode();
		Icons.setLargeIcon(this, langCode);
	}
}
