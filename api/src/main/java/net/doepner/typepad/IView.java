package net.doepner.typepad;

import java.awt.Image;

import net.doepner.lang.ILanguage;
import net.doepner.ui.Editor;
import net.doepner.ui.IAction;

/**
 * Application view interface
 */
public interface IView {

    void show();

    void showImage(Image image);

    void setActions(IAction... actions);

    void setLanguage(ILanguage language);

    Editor getEditor();
}
