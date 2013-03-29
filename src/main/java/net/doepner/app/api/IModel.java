package net.doepner.app.api;

import net.doepner.lang.LanguageChanger;
import net.doepner.text.TextModel;
import net.doepner.text.WordProvider;

/**
 * Aplpication model interface
 */
public interface IModel extends TextModel, WordProvider, LanguageChanger {

    String getText();

    void setText(String text);

    void nextBuffer();

    int getCurrentBuffer();
}
