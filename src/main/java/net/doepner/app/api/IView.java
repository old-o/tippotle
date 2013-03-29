package net.doepner.app.api;

import java.awt.Image;

import net.doepner.text.TextCoordinates;
import net.doepner.ui.HasFont;
import net.doepner.ui.action.IdAction;

/**
 * Application view interface
 */
public interface IView extends TextCoordinates, HasFont {

    void show();

    void showImage(Image image);

    void setActions(Iterable<IdAction> actions);

}
