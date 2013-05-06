package net.doepner.typepad.action;

import net.doepner.i18n.L10nMapper;
import net.doepner.ui.ActionId;

import static net.doepner.lang.Language.DEUTSCH;
import static net.doepner.lang.Language.ENGLISH;

/**
 * Localized action descriptions
 */
public class ActionDescriptions extends L10nMapper<ActionId, String> {

    {
        put(DEUTSCH, ActionEnum.SWITCH_BUFFER, "Textspeicher wechseln");
        put(DEUTSCH, ActionEnum.SWITCH_LANGUAGE, "Sprache wechseln");
        put(DEUTSCH, ActionEnum.SMALLER_FONT, "Schrift verkleinern");
        put(DEUTSCH, ActionEnum.BIGGER_FONT, "Schrift vergrößern");
        put(DEUTSCH, ActionEnum.SPEAK_WORD, "Wort vorlesen");

        put(ENGLISH, ActionEnum.SWITCH_BUFFER, "Switch buffer");
        put(ENGLISH, ActionEnum.SWITCH_LANGUAGE, "Switch language");
        put(ENGLISH, ActionEnum.SMALLER_FONT, "Decrease font size");
        put(ENGLISH, ActionEnum.BIGGER_FONT, "Increase font size");
        put(ENGLISH, ActionEnum.SPEAK_WORD, "Speak word");
    }
}
