package net.doepner.app.typepad;

import net.doepner.lang.LanguageChanger;
import net.doepner.text.TextModel;
import net.doepner.text.WordProvider;

/**
 * Application model interface
 */
public interface IModel extends TextModel, WordProvider, LanguageChanger {

    String getText();

    void setText(String text);

    void nextBuffer();

    int getCurrentBuffer();
}
