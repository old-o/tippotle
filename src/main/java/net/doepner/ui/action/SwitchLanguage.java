package net.doepner.ui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.doepner.lang.LanguageChanger;
import net.doepner.ui.icons.IconLoader;

public class SwitchLanguage extends AbstractAction implements Action {

	private final LanguageChanger langChanger;
	
	public SwitchLanguage(LanguageChanger langChanger) {
		this.langChanger = langChanger;
		update();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		langChanger.changeLanguage();
		update();
	}

	private void update() {
		final String langCode = langChanger.getLanguage().getCode();
		putValue(Action.LARGE_ICON_KEY, IconLoader.load(langCode + ".png"));
	}

    @Override
    public ActionId getId() {
        return ActionId.SWITCH_LANGUAGE;
    }
}
