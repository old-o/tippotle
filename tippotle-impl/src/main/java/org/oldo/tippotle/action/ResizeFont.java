package org.oldo.tippotle.action;

import org.oldo.ui.Action;
import org.oldo.ui.FontResizable;

import static org.oldo.tippotle.action.ActionEnum.BIGGER_FONT;
import static org.oldo.tippotle.action.ActionEnum.SMALLER_FONT;

public final class ResizeFont implements Action {

    private final int step;
    private final FontResizable fontResizable;

    public ResizeFont(int step, FontResizable fontResizable) {
        this.step = step;
        this.fontResizable = fontResizable;
    }

    @Override
    public void execute() {
        fontResizable.resizeFont(step);
    }

    @Override
    public ActionEnum id() {
        return step > 0 ? BIGGER_FONT : SMALLER_FONT;
    }
}
