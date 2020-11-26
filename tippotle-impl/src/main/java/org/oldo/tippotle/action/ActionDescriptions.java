package org.oldo.tippotle.action;

import org.oldo.i18n.L10n;
import org.oldo.lang.Language;
import org.oldo.ui.ActionId;

import static org.oldo.lang.LanguageEnum.DEUTSCH;

/**
 * Localized action descriptions
 */
public final class ActionDescriptions implements L10n<ActionId, String> {

    private final Class<ActionEnum> actionEnumClass = ActionEnum.class;

    @Override
    public String get(ActionId actionId, Language language) {
        if (actionEnumClass.isInstance(actionId)) {
            final ActionEnum actionEnum = actionEnumClass.cast(actionId);
            return DEUTSCH.equals(language) ? de(actionEnum) : en(actionEnum);
        } else {
            throw new IllegalArgumentException("Unknown action id: " + actionId);
        }
    }

    @SuppressWarnings({"OverlyComplexMethod", "SuppressionAnnotation"})
    private static String de(ActionEnum actionEnum) {
        switch (actionEnum) {
            case SWITCH_DOCUMENT:
                return "Text-Dokument wechseln";
            case SWITCH_LANGUAGE:
                return "Sprache wechseln";
            case SMALLER_FONT:
                return "Schrift verkleinern";
            case BIGGER_FONT:
                return "Schrift vergrößern";
            case SWITCH_SPEAKER:
                return "Sprecher wechseln";
            case EMAIL:
                return "Email versenden";
            case SPEAK_ALL:
                return "Ganzen Text vorlesen";
            case SPEAK_WORD:
                return "Wort unter Cursor vorlesen";
            case STOP_AUDIO:
                return "Alle Sprach/Ton-Ausgabe beenden";
            default:
                throw new IllegalArgumentException("Unbekannte action id: " + actionEnum);
        }
    }

    @SuppressWarnings({"OverlyComplexMethod", "SuppressionAnnotation"})
    private static String en(ActionEnum commandId) {
        switch (commandId) {
            case SWITCH_DOCUMENT:
                return "Switch document";
            case SWITCH_LANGUAGE:
                return "Switch language";
            case SMALLER_FONT:
                return "Decrease font size";
            case BIGGER_FONT:
                return "Increase font size";
            case SWITCH_SPEAKER:
                return "Switch speaker";
            case EMAIL:
                return "Send email";
            case SPEAK_ALL:
                return "Speak the whole text";
            case SPEAK_WORD:
                return "Speak the current word";
            case STOP_AUDIO:
                return "Stop all sound output";
            default:
                throw new IllegalArgumentException("Invalid action id: " + commandId);
        }
    }
}
