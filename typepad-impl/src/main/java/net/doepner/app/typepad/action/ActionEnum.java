package net.doepner.app.typepad.action;

import net.doepner.ui.ActionId;

/**
 * Action ids
 */
public enum ActionEnum implements ActionId {

    SWITCH_BUFFER("buffers"),
    SWITCH_LANGUAGE,
    BIGGER_FONT("zoom-in"),
    SMALLER_FONT("zoom-out"),
    SPEAK_WORD("volume"),
    SWITCH_SPEAKER,
    EMAIL("email"),
    SPEAK_ALL("speech-bubble"),
    STOP_AUDIO("stop-audio");

    private final String iconName;

    ActionEnum() {
        this(null);
    }

    ActionEnum(String iconName) {
        this.iconName = iconName;
    }

    @Override
    public String iconName() {
        return iconName;
    }
}
