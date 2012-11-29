package net.doepner.typepad;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.text.DefaultStyledDocument;

import net.doepner.event.ChangeListener;
import net.doepner.i18n.L10n;
import net.doepner.lang.EnglishOrGerman;
import net.doepner.lang.Language;
import net.doepner.lang.LanguageChanger;
import net.doepner.speech.ESpeaker;
import net.doepner.speech.Speaker;
import net.doepner.text.WordExtractor;
import net.doepner.ui.Showable;
import net.doepner.ui.action.ActionId;
import net.doepner.ui.action.IdAction;
import net.doepner.ui.action.ResizeFont;
import net.doepner.ui.action.SpeakWord;
import net.doepner.ui.action.SwitchBuffer;
import net.doepner.ui.action.SwitchLanguage;
import net.doepner.ui.i18n.ActionDescriptions;
import net.doepner.ui.icons.IconLoader;
import net.doepner.ui.text.TextChangeHandler;
import net.doepner.ui.text.TextChangeListener;
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

        final UiTextModel textModel = new UiTextModel(pane);

        pane.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                handleWord(new WordExtractor(textModel).getText());
            }
        });
        doc.addDocumentListener(new TextChangeListener(
                new TextChangeHandler() {
                    @Override
                    public void handleChange(DocumentEvent e) {
                        handleWord(new WordExtractor(e).getText());
                    }
                })
        );

        final List<IdAction> actions = new LinkedList<IdAction>(Arrays.asList(
                new SwitchLanguage(languageChanger),
                new SpeakWord(new WordExtractor(textModel), speaker),
                new ResizeFont(-1, pane), new ResizeFont(+1, pane),
                new SwitchBuffer(BUFFER_COUNT, textModel, textModel)));

        for (IdAction action : actions) {
            final Icon icon = IconLoader.getIcon(action.getId());
            action.putValue(Action.LARGE_ICON_KEY, icon);
        }

        languageChanger.addListener(new ChangeListener() {
            @Override
            public void handleChange() {
                for (IdAction action : actions) {
                    final Language lang = languageChanger.getLanguage();
                    final String descr = actionDescr.get(lang, action.getId());
                    action.putValue(Action.SHORT_DESCRIPTION, descr);
                }
            }
        });

        typePad = new TypePad(pane, new LinkedList<Action>(actions));
    }

    private void handleWord(String word) {
        // TODO: Show an image if one matches the word
        System.out.println("word: " + word);
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
