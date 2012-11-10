package net.doepner.typepad;

import java.util.Arrays;
import java.util.List;

import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultStyledDocument;

import net.doepner.event.ChangeListener;
import net.doepner.lang.EnglishOrGerman;
import net.doepner.lang.Language;
import net.doepner.lang.LanguageChanger;
import net.doepner.speech.ESpeaker;
import net.doepner.speech.Speaker;
import net.doepner.text.WordExtractor;
import net.doepner.ui.Showable;
import net.doepner.ui.action.Action;
import net.doepner.ui.action.ActionId;
import net.doepner.ui.action.ResizeFont;
import net.doepner.ui.action.SpeakWord;
import net.doepner.ui.action.SwitchBuffer;
import net.doepner.ui.action.SwitchLanguage;
import net.doepner.ui.i18n.ActionDescriptions;
import net.doepner.i18n.L10n;
import net.doepner.ui.text.UiTextModel;

import static net.doepner.typepad.DocUtil.prepareDocument;

public class App {

    private static final int BUFFER_COUNT = 5;

    public static void main(String[] args) {
        new App().run();
    }

    private final L10n<ActionId> actionDescr =
            new ActionDescriptions();

    private final Showable typePad;

    public App() {
        final LanguageChanger languageChanger = new EnglishOrGerman();
        final Speaker speaker = new ESpeaker(languageChanger);

        final DefaultStyledDocument doc = prepareDocument(speaker);

        final JTextPane pane = new JTextPane(doc);

        final List<Action> actions = getActions(
                languageChanger, speaker, pane);

        languageChanger.addListener(new ChangeListener() {
            @Override
            public void handleChange() {
                for (Action action : actions) {
                    final Language lang = languageChanger.getLanguage();
                    final String descr = actionDescr.get(lang, action.getId());
                    action.putValue(Action.SHORT_DESCRIPTION, descr);
                }
            }
        });

        typePad = new TypePad(pane, actions);
    }

    private static List<Action> getActions(LanguageChanger languageChanger,
                                    Speaker speaker, JTextPane pane) {

        final Action switchLangAction = new SwitchLanguage(
                languageChanger);

        final Action biggerFontAction = new ResizeFont(+1, pane);
        final Action smallerFontAction = new ResizeFont(-1, pane);

        final UiTextModel textModel = new UiTextModel(pane);

        final Action speakWordAction = new SpeakWord(
                new WordExtractor(textModel), speaker);

        final Action switchBufferAction = new SwitchBuffer(
                BUFFER_COUNT, textModel, textModel);

        return Arrays.asList(switchLangAction, speakWordAction,
                smallerFontAction, biggerFontAction,
                switchBufferAction);
    }

    private void run() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                typePad.show();
            }
        });
    }

}
