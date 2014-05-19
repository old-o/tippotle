package net.doepner.app.typepad;

import java.net.URL;

import net.doepner.lang.Language;
import net.doepner.ui.Editor;
import net.doepner.ui.EmailDialog;
import net.doepner.ui.IAction;

/**
 * Application view interface
 */
public interface IView {

    void show();

    void showWordImages(Iterable<URL> images);

    void showCharImages(Iterable<URL> images);

    void setActions(IAction... actions);

    void setLanguage(Language language);

    Editor getEditor();

    EmailDialog getEmailDialog();
}
