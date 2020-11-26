package org.oldo.tippotle;

import org.guppy4j.event.ChangeSupport;
import org.guppy4j.log.Log;
import org.guppy4j.log.LogProvider;
import org.guppy4j.log.Slf4jLogProvider;
import org.guppy4j.sound.AudioPlayer;
import org.guppy4j.sound.ConvertingStreamPlayer;
import org.guppy4j.sound.DirectStreamPlayer;
import org.guppy4j.sound.StdAudioPlayer;
import org.oldo.file.ApplicationFiles;
import org.oldo.file.StdApplicationFiles;
import org.oldo.file.TextBuffers;
import org.oldo.file.TextFiles;
import org.oldo.lang.CanadianDeutsch;
import org.oldo.lang.LanguageChanger;
import org.oldo.mail.Emailer;
import org.oldo.mail.NoEmailer;
import org.oldo.mail.SmtpConfig;
import org.oldo.mail.SmtpEmailer;
import org.oldo.resources.CascadingResourceFinder;
import org.oldo.resources.ClasspathFinder;
import org.oldo.resources.FileDownload;
import org.oldo.resources.FileFinder;
import org.oldo.resources.GoogleTranslateUrls;
import org.oldo.resources.ResourceFinder;
import org.oldo.resources.StdImageCollector;
import org.oldo.resources.StdResourceCollector;
import org.oldo.speech.AudioFileSpeaker;
import org.oldo.speech.ESpeaker;
import org.oldo.speech.IterableSpeakers;
import org.oldo.speech.ManagedSpeakers;
import org.oldo.text.CurrentTextTracker;
import org.oldo.text.DocumentModel;
import org.oldo.text.SubStringFactory;
import org.oldo.text.WordExtractor;
import org.oldo.text.WordProvider;
import org.oldo.tippotle.action.ActionDescriptions;
import org.oldo.tippotle.action.EmailAction;
import org.oldo.tippotle.action.ResizeFont;
import org.oldo.tippotle.action.SpeakAll;
import org.oldo.tippotle.action.SpeakWord;
import org.oldo.tippotle.action.StopAudioAction;
import org.oldo.tippotle.action.SwitchDocument;
import org.oldo.tippotle.action.SwitchLanguage;
import org.oldo.tippotle.action.SwitchSpeaker;
import org.oldo.tippotle.ui.ActionConverter;
import org.oldo.tippotle.ui.EditorToolBar;
import org.oldo.tippotle.ui.ImageBar;
import org.oldo.ui.Action;
import org.oldo.ui.CharStyler;
import org.oldo.ui.Editor;
import org.oldo.ui.EmailDialog;
import org.oldo.ui.FontChooser;
import org.oldo.ui.SwingEditor;
import org.oldo.ui.SwingEmailDialog;
import org.oldo.ui.SwingFrame;
import org.oldo.ui.images.ImagesUpdater;
import org.oldo.ui.text.AlphaNumStyler;
import org.oldo.ui.text.ColorRotation;
import org.oldo.ui.text.Documents;
import org.oldo.ui.text.LoopingTextSpanStyler;
import org.oldo.ui.text.TextStyler;
import org.oldo.ui.text.UndoManagement;
import org.oldo.util.ConcurrentCache;
import org.oldo.util.WordsOnly;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyledDocument;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.lang.Runtime.getRuntime;
import static java.lang.String.valueOf;
import static java.lang.Thread.setDefaultUncaughtExceptionHandler;
import static org.guppy4j.io.PathType.FILE;
import static org.guppy4j.log.Log.Level.error;
import static org.oldo.ui.SwingUtil.doInBackground;

/**
 * Wires up the application
 * <p>
 * Object creation, constructor injection and listener registration is the
 * single responsibility of this class. That's why it is the only class in
 * the whole codebase that must be "overly coupled" and has an "overly long"
 * constructor.
 * </p>
 */
@SuppressWarnings({
        "OverlyCoupledClass",
        "SuppressionAnnotation"
})
public final class Application {

    static final String ESPEAKER_NAME = "robbi";
    static final String GOOGLE_SPEAKER_NAME = "google-translate";

    private static final int NUMBER_OF_TEXT_BUFFERS = 5;

    private final Log log;
    private final SwingFrame frame;

    @SuppressWarnings({
            "OverlyLongMethod",
            "SuppressionAnnotation",
            "OverlyCoupledMethod"
    })
    public Application() {

        final LogProvider logProvider = new Slf4jLogProvider();
        log = logProvider.getLog(getClass());

        setDefaultUncaughtExceptionHandler((t, e) -> log.as(error, e));

        final String appShortName = "tippotle";
        final String appTitle = "Tippotle - Typing for fun";

        final Path homeDir = Paths.get(System.getProperty("user.home"));

        final ApplicationFiles applicationFiles =
                new StdApplicationFiles(logProvider, appShortName, homeDir);

        final ResourceFinder resourceFinder = new CascadingResourceFinder(
                new FileFinder(applicationFiles),
                new ClasspathFinder(logProvider),
                new FileDownload(logProvider, applicationFiles, new GoogleTranslateUrls()),
                new WordsOnly());

        final AudioPlayer audioPlayer = new StdAudioPlayer(logProvider,
                new DirectStreamPlayer(),
                new ConvertingStreamPlayer(logProvider));

        final LanguageChanger languageChanger =
                new CanadianDeutsch(logProvider, new ChangeSupport<>());

        final IterableSpeakers speakers = new ManagedSpeakers(
                logProvider,
                new AudioFileSpeaker(GOOGLE_SPEAKER_NAME,
                        resourceFinder, languageChanger, audioPlayer),
                new ESpeaker(languageChanger, ESPEAKER_NAME)
        );

        final Function<String, Iterable<Image>> imageCollector =
                new ConcurrentCache<>(new StdImageCollector(
                        new StdResourceCollector(resourceFinder), 10, logProvider));

        final Path configFile = applicationFiles.findOrCreate("email.properties", FILE);
        final SmtpConfig emailConfig = loadEmailConfig(configFile);

        final Emailer emailer = emailConfig == null
                ? new NoEmailer(logProvider)
                : new SmtpEmailer(emailConfig, logProvider);

        /* MODEL */

        final TextBuffers buffers = new TextFiles(logProvider, applicationFiles);
        final DocumentListener textStyler = new TextStyler(new AlphaNumStyler(), logProvider);

        final Function<String, StyledDocument> docInitializer = text -> {
            final StyledDocument doc = new DefaultStyledDocument();
            doc.addDocumentListener(textStyler);
            try {
                doc.insertString(0, text, null);
            } catch (BadLocationException e) {
                throw new IllegalStateException(e);
            }
            return doc;
        };

        final DocumentModel documentModel =
                new Documents(NUMBER_OF_TEXT_BUFFERS, docInitializer, buffers, logProvider);

        final WordProvider wordProvider = new WordExtractor(logProvider, documentModel, new SubStringFactory());

        /* VIEW */

        final JTextPane textPane = new JTextPane();
        final int editorFontSize = 40;
        textPane.setFont(new Font("serif", Font.PLAIN, editorFontSize));

        @SuppressWarnings("MagicNumber")
        final Color caretColorMask = new Color(0, 50, 50);

        final Editor editor = new SwingEditor(textPane, caretColorMask);

        final EmailDialog emailDialog = new SwingEmailDialog(imageCollector, emailConfig);

        final Action[] actions = {
                new SwitchLanguage(languageChanger),
                new SpeakWord(editor, wordProvider, speakers),
                new ResizeFont(-1, editor),
                new ResizeFont(1, editor),
                new SwitchDocument(documentModel),
                new SwitchSpeaker(speakers),
                new EmailAction(emailDialog, documentModel, emailer, speakers),
                new SpeakAll(documentModel, speakers),
                new StopAudioAction(speakers)
        };


        final Supplier<JToolBar> toolBar = new EditorToolBar(
                logProvider,
                new FontChooser(textPane),
                new ActionConverter(editor, new ActionDescriptions(), new IconResources(), languageChanger),
                actions);

        final Dimension imagePanelSize = new Dimension(100, 100);
        final ImageBar charImageBar = ImageBar.vertical(imagePanelSize, 5);
        final ImageBar wordImageBar = ImageBar.horizontal(imagePanelSize, 5);

        final int frameWidth = 800;
        final int frameHeight = 600;

        frame = new SwingFrame(appTitle,
                new Dimension(frameWidth, frameHeight),
                toolBar.get(), new JScrollPane(textPane),
                charImageBar.get(),
                wordImageBar.get());


        /* Listeners */

        documentModel.addDocSwitchListener(new UndoManagement(logProvider, textPane));

        addShutdownHook(documentModel::switchDocument);

        documentModel.addTextListener(text -> {
            if (text.length() == 1) {
                doInBackground(() -> speakers.speak(text));
            }
        });

        documentModel.addDocSwitchListener(textPane::setDocument);

        final CharStyler colorRotation = new ColorRotation();

        editor.addTextPositionListener(new CurrentTextTracker(wordProvider,
                new ImagesUpdater<>(charImageBar, character -> imageCollector.apply(valueOf(character))),
                new ImagesUpdater<>(wordImageBar, textSpan -> imageCollector.apply(textSpan.getContent())),
                new LoopingTextSpanStyler(documentModel, colorRotation, 100)
        ));


        // on every word change, apply certain style attributes to current word


    }

    private SmtpConfig loadEmailConfig(Path configFile) {
        try {
            return new SmtpConfig(configFile);
        } catch (IOException e) {
            log.as(error, e);
            return null;
        }
    }

    private static void addShutdownHook(Runnable runnable) {
        getRuntime().addShutdownHook(new Thread(runnable));
    }

    public void start() {
        frame.show();
    }
}
