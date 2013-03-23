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
import javax.swing.text.StyledDocument;

import net.doepner.file.FileHelper;
import net.doepner.file.IFileHelper;
import net.doepner.file.ImageFiles;
import net.doepner.file.TextFiles;
import net.doepner.lang.EnglishOrGerman;
import net.doepner.lang.LanguageChanger;
import net.doepner.speech.ESpeaker;
import net.doepner.speech.Speaker;
import net.doepner.text.TextCoordinates;
import net.doepner.text.WordExtractor;
import net.doepner.ui.IconL10nUpdater;
import net.doepner.ui.Showable;
import net.doepner.ui.action.IdAction;
import net.doepner.ui.action.ResizeFont;
import net.doepner.ui.action.SpeakWord;
import net.doepner.ui.action.SwitchBuffer;
import net.doepner.ui.action.SwitchLanguage;
import net.doepner.ui.icons.IconLoader;
import net.doepner.ui.images.ImageHelper;
import net.doepner.ui.images.ImageMap;
import net.doepner.ui.text.DocTextModel;
import net.doepner.ui.text.TextChangeListener;
import net.doepner.ui.text.TextStyler;

public class App {

    public static void main(String[] args) {
        new App().run();
    }

    private static final int BUFFER_COUNT = 5;

    private final IFileHelper fileHelper = new FileHelper("typepad");

    private final ImageMap imageMap =
            new ImageHelper(new ImageFiles(fileHelper));

    private final Speaker speaker;
    private final Showable typePad;

    public App() {
        final LanguageChanger languageChanger = new EnglishOrGerman();
        speaker = new ESpeaker(languageChanger);

        final StyledDocument doc = new DefaultStyledDocument();
        doc.addDocumentListener(new TextStyler(new AlphaNumStyler()));

        final JTextPane pane = new JTextPane(doc);
        final DocTextModel textModel = new DocTextModel(doc);
        final WordExtractor wordExtractor = new WordExtractor(textModel);

        final List<IdAction> actions = getActions(languageChanger,
                pane, textModel, wordExtractor);

        typePad = new TypePad(pane, new LinkedList<Action>(actions));

        languageChanger.addListener(new IconL10nUpdater(actions));

        pane.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                int position = e.getDot();
                System.out.println(position);
                showImage(wordExtractor.getText(position));
            }
        });

        doc.addDocumentListener(new TextChangeListener() {
            @Override
            public void handleChange(DocumentEvent e) {
                speaker.speak(getText(e));
            }
        });
    }

    private List<IdAction> getActions(final LanguageChanger languageChanger,
                                      final JTextPane pane,
                                      final DocTextModel textModel,
                                      final WordExtractor wordExtractor) {

        final List<IdAction> actions = new LinkedList<IdAction>(Arrays.asList(
                new SwitchLanguage(languageChanger),
                new SpeakWord(wordExtractor, speaker, new TextCoordinates() {
                    @Override
                    public int getPosition() {
                        return pane.getCaretPosition();
                    }
                }),
                new ResizeFont(-1, pane), new ResizeFont(+1, pane),
                new SwitchBuffer(BUFFER_COUNT, textModel,
                        new TextFiles(fileHelper))));

        for (IdAction action : actions) {
            if (action.getValue(Action.LARGE_ICON_KEY) == null) {
                final Icon icon = IconLoader.getIcon(action.getId());
                action.putValue(Action.LARGE_ICON_KEY, icon);
            }
        }
        return actions;
    }

    private void showImage(String word) {
        typePad.showImage(imageMap.getImage(word));
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
