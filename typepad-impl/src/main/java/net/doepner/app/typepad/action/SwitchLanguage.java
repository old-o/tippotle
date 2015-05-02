package net.doepner.app.typepad.action;

import net.doepner.lang.LanguageChanger;
import net.doepner.ui.IAction;

public final class SwitchLanguage implements IAction {

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
    public String iconName() {
        return langChanger.language().code();
    }
}
