package net.doepner.app.tippotle;

import net.doepner.app.tippotle.action.ActionDescriptions;
import net.doepner.app.tippotle.action.EmailAction;
import net.doepner.app.tippotle.action.ResizeFont;
import net.doepner.app.tippotle.action.SpeakAll;
import net.doepner.app.tippotle.action.SpeakWord;
import net.doepner.app.tippotle.action.StopAudioAction;
import net.doepner.app.tippotle.action.SwitchDocument;
import net.doepner.app.tippotle.action.SwitchLanguage;
import net.doepner.app.tippotle.action.SwitchSpeaker;
import net.doepner.app.tippotle.ui.ActionConverter;
import net.doepner.app.tippotle.ui.EditorToolBar;
import net.doepner.app.tippotle.ui.ImageBar;
import net.doepner.file.ApplicationFiles;
import net.doepner.file.StdApplicationFiles;
import net.doepner.file.TextBuffers;
import net.doepner.file.TextFiles;
import net.doepner.lang.CanadianDeutsch;
import net.doepner.lang.LanguageChanger;
import net.doepner.mail.Emailer;
import net.doepner.mail.NoEmailer;
import net.doepner.mail.SmtpConfig;
import net.doepner.mail.SmtpEmailer;
import net.doepner.resources.CascadingResourceFinder;
import net.doepner.resources.ClasspathFinder;
import net.doepner.resources.FileDownload;
import net.doepner.resources.FileFinder;
import net.doepner.resources.GoogleTranslateUrls;
import net.doepner.resources.ResourceFinder;
import net.doepner.resources.StdImageCollector;
import net.doepner.resources.StdResourceCollector;
import net.doepner.speech.AudioFileSpeaker;
import net.doepner.speech.ESpeaker;
import net.doepner.speech.IterableSpeakers;
import net.doepner.speech.ManagedSpeakers;
import net.doepner.text.DocumentModel;
import net.doepner.text.TextImagesUpdater;
import net.doepner.text.WordExtractor;
import net.doepner.text.WordProvider;
import net.doepner.ui.Action;
import net.doepner.ui.Editor;
import net.doepner.ui.EmailDialog;
import net.doepner.ui.FontChooser;
import net.doepner.ui.SwingEditor;
import net.doepner.ui.SwingEmailDialog;
import net.doepner.ui.SwingFrame;
import net.doepner.ui.text.AlphaNumStyler;
import net.doepner.ui.text.Documents;
import net.doepner.ui.text.TextStyler;
import net.doepner.ui.text.UndoManagement;
import net.doepner.util.ConcurrentCache;
import net.doepner.util.WordsOnly;
import org.guppy4j.event.ChangeSupport;
import org.guppy4j.log.Log;
import org.guppy4j.log.LogProvider;
import org.guppy4j.log.Slf4jLogProvider;
import org.guppy4j.sound.AudioPlayer;
import org.guppy4j.sound.ConvertingStreamPlayer;
import org.guppy4j.sound.DirectStreamPlayer;
import org.guppy4j.sound.StdAudioPlayer;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
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
import static java.lang.Thread.setDefaultUncaughtExceptionHandler;
import static net.doepner.ui.SwingUtil.doInBackground;
import static org.guppy4j.io.PathType.FILE;
import static org.guppy4j.log.Log.Level.error;

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

        final String appName = "Tippotle - Oh yeah!";
        final Path homeDir = Paths.get(System.getProperty("user.home"));

        final ApplicationFiles applicationFiles =
                new StdApplicationFiles(logProvider, appName, homeDir);

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

        final Function<String, Document> docInitializer = text -> {
            final Document doc = new DefaultStyledDocument();
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

        final WordProvider wordProvider = new WordExtractor(logProvider, documentModel);

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

        frame = new SwingFrame(appName,
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

        editor.addTextPositionListener(new TextImagesUpdater(
                wordProvider, charImageBar, wordImageBar, imageCollector));

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
