package net.doepner.typepad.action;

import net.doepner.ui.ActionId;

/**
 * Action ids
 */
public enum ActionEnum implements ActionId {

    SWITCH_BUFFER("buffers.png"),
    SWITCH_LANGUAGE,
    BIGGER_FONT("zoom-in.png"),
    SMALLER_FONT("zoom-out.png"),
    SPEAK_WORD("volume.png"),
    SWITCH_SPEAKER("volume.png");

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
