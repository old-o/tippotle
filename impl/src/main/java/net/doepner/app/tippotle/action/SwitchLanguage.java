package net.doepner.app.tippotle.action;

import net.doepner.lang.LanguageChanger;
import net.doepner.ui.Action;

public final class SwitchLanguage implements Action {

    private final LanguageChanger langChanger;

    public SwitchLanguage(LanguageChanger langChanger) {
        this.langChanger = langChanger;
    }

    @Override
    public void execute() {
        langChanger.changeLanguage();
    }

    @Override
    public ActionEnum id() {
        return ActionEnum.SWITCH_LANGUAGE;
    }

    @Override
    public Object iconKey() {
        return langChanger.language();
    }
}
