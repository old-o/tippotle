package net.doepner.typepad.action;

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
    SWITCH_SPEAKER("volume");

    private final String iconName;

    private ActionEnum() {
        this.iconName = null;
    }

    private ActionEnum(String iconName) {
        this.iconName = iconName;
    }

    public String getIconName() {
        return iconName;
    }
}
