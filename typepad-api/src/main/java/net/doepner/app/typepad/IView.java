package net.doepner.app.typepad;

import net.doepner.lang.Language;
import net.doepner.ui.Editor;
import net.doepner.ui.EmailDialog;
import net.doepner.ui.IAction;

import java.awt.Image;

/**
 * Application view interface
 */
public interface IView {

    void show();

    void showWordImages(Iterable<Image> images);

    void showCharImages(Iterable<Image> images);

    void setActions(IAction... actions);

    void setLanguage(Language language);

    Editor getEditor();

    EmailDialog getEmailDialog();
}
