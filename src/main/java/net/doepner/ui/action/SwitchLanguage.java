package net.doepner.ui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.doepner.lang.LanguageChanger;

import static net.doepner.ui.icons.IconLoader.getIcon;

public class SwitchLanguage extends AbstractAction implements IdAction {

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
        putValue(LARGE_ICON_KEY, getIcon(langCode + ".png"));
    }

    @Override
    public ActionId getId() {
        return ActionId.SWITCH_LANGUAGE;
    }
}
