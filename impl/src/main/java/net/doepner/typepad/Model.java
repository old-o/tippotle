package net.doepner.typepad;

import net.doepner.event.ChangeListener;
import net.doepner.lang.ILanguage;
import net.doepner.lang.LanguageChanger;
import net.doepner.text.TextListener;
import net.doepner.text.TextModel;
import net.doepner.text.WordExtractor;
import net.doepner.text.WordProvider;

/**
 * Application model (facade for several model elements)
 */
public class Model implements IModel {

    private static final int MAX_BUFFER = 5;

    private final TextModel textModel;
    private final LanguageChanger languageChanger;
    private final WordProvider wordProvider;

    private int currentBuffer = 1;

    public Model(TextModel textModel, IContext context) {
        this.textModel = textModel;
        this.languageChanger = context.getLanguageChanger();
        wordProvider = new WordExtractor(textModel);
    }

    @Override
    public String getText() {
        return textModel.getText();
    }

    @Override
    public void setText(String text) {
        textModel.setText(text);
    }

    @Override
    public void addTextListener(TextListener listener) {
        textModel.addTextListener(listener);
    }

    @Override
    public int getCurrentBuffer() {
        return currentBuffer;
    }

    @Override
    public void nextBuffer() {
        currentBuffer = currentBuffer % MAX_BUFFER + 1;
    }

    @Override
    public String getWord(int position) {
        return wordProvider.getWord(position);
    }

    @Override
    public char getCharacter(int position) {
        return wordProvider.getCharacter(position);
    }

    @Override
    public void changeLanguage() {
        languageChanger.changeLanguage();
    }

    @Override
    public ILanguage getLanguage() {
        return languageChanger.getLanguage();
    }

    @Override
    public void addListener(ChangeListener<ILanguage> listener) {
        languageChanger.addListener(listener);
    }
}
