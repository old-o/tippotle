package net.doepner.app.typepad.action;

import net.doepner.ui.FontResizable;
import net.doepner.ui.IAction;

public class ResizeFont implements IAction {

    private final int step;
    private final FontResizable fontResizable;

    public ResizeFont(int step, FontResizable fontResizable) {
        this.step = step;
        this.fontResizable = fontResizable;
    }

    @Override
    public void actionPerformed() {
        fontResizable.resizeFont(step);
    }

    @Override
    public ActionEnum getId() {
        return step > 0 ? ActionEnum.BIGGER_FONT : ActionEnum.SMALLER_FONT;
    }

    @Override
    public String getIconName() {
        return getId().getIconName();
    }
}
