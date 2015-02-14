package net.doepner.app.typepad.action;

import net.doepner.lang.LanguageChanger;
import net.doepner.ui.IAction;

public class SwitchLanguage implements IAction {

    private final LanguageChanger langChanger;

    public SwitchLanguage(LanguageChanger langChanger) {
        this.langChanger = langChanger;
    }

    @Override
    public void execute() {
        langChanger.changeLanguage();
    }

    @Override
    public ActionEnum getId() {
        return ActionEnum.SWITCH_LANGUAGE;
    }

    @Override
    public String getIconName() {
        return langChanger.getLanguage().getCode();
    }
}
