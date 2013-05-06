package net.doepner.typepad;

import java.awt.Image;

import net.doepner.lang.ILanguage;
import net.doepner.text.TextCoordinates;
import net.doepner.ui.FontResizable;
import net.doepner.ui.IAction;

/**
 * Application view interface
 */
public interface IView extends TextCoordinates, FontResizable {

    void show();

    void showImage(Image image);

    void setActions(Iterable<IAction> actions);

    void setLanguage(ILanguage language);
}
