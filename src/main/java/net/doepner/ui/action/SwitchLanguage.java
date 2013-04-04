package net.doepner.ui.action;

import net.doepner.lang.LanguageChanger;

public class SwitchLanguage implements IAction {

    private final LanguageChanger langChanger;

    public SwitchLanguage(LanguageChanger langChanger) {
        this.langChanger = langChanger;
    }

    @Override
    public void actionPerformed() {
        langChanger.changeLanguage();
    }

    @Override
    public ActionId getId() {
        return ActionId.SWITCH_LANGUAGE;
    }
}
