package net.doepner.app;

import net.doepner.app.api.IContext;
import net.doepner.app.api.IModel;
import net.doepner.event.ChangeListener;
import net.doepner.lang.Language;
import net.doepner.lang.LanguageChanger;
import net.doepner.text.TextModel;
import net.doepner.text.WordExtractor;
import net.doepner.text.WordProvider;

/**
 * Application model
 */
public class Model implements IModel {

    private final TextModel textModel;
    private final LanguageChanger languageChanger;
    private final WordProvider wordProvider;

    private final int maxBuffer = 5;
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
    public int getCurrentBuffer() {
        return currentBuffer;
    }

    @Override
    public void nextBuffer() {
        currentBuffer = currentBuffer % maxBuffer + 1;
    }

    @Override
    public String getWord(int position) {
        return wordProvider.getWord(position);
    }

    @Override
    public void changeLanguage() {
        languageChanger.changeLanguage();
    }

    @Override
    public Language getLanguage() {
        return languageChanger.getLanguage();
    }

    @Override
    public void addListener(ChangeListener<Language> listener) {
        languageChanger.addListener(listener);
    }
}
