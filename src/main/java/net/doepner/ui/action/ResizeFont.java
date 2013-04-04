package net.doepner.ui.action;

import net.doepner.ui.FontResizable;

public class ResizeFont implements IAction {

    private final int step;
    private final FontResizable fontResizable;

    public ResizeFont(int step, FontResizable fontResizable) {
        this.step = step;
        this.fontResizable = fontResizable;
    }

    @Override
    public void actionPerformed() {
        fontResizable.resize(step);
    }

    @Override
    public ActionId getId() {
        return step > 0 ? ActionId.BIGGER_FONT : ActionId.SMALLER_FONT;
    }
}
