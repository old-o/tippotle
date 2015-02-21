package net.doepner.app.typepad;

import net.doepner.app.typepad.action.ActionDescriptions;
import net.doepner.app.typepad.action.EmailAction;
import net.doepner.app.typepad.action.ResizeFont;
import net.doepner.app.typepad.action.SpeakAll;
import net.doepner.app.typepad.action.SpeakWord;
import net.doepner.app.typepad.action.StopAudioAction;
import net.doepner.app.typepad.action.SwitchDocument;
import net.doepner.app.typepad.action.SwitchLanguage;
import net.doepner.app.typepad.action.SwitchSpeaker;
import net.doepner.app.typepad.ui.SwingFrame;
import net.doepner.file.PathHelper;
import net.doepner.file.PathType;
import net.doepner.file.StdPathHelper;
import net.doepner.file.TextBuffers;
import net.doepner.file.TextFiles;
import net.doepner.lang.CanadianDeutsch;
import net.doepner.lang.LanguageChanger;
import net.doepner.log.Log;
import net.doepner.log.LogProvider;
import net.doepner.log.Slf4jLogProvider;
import net.doepner.mail.Emailer;
import net.doepner.mail.NoEmailer;
import net.doepner.mail.SmtpConfig;
import net.doepner.mail.SmtpEmailer;
import net.doepner.resources.CascadingResourceFinder;
import net.doepner.resources.ClasspathFinder;
import net.doepner.resources.FileDownload;
import net.doepner.resources.FileFinder;
import net.doepner.resources.GoogleTranslateUrls;
import net.doepner.resources.ImageCollector;
import net.doepner.resources.ResourceFinder;
import net.doepner.resources.StdImageCollector;
import net.doepner.resources.StdResourceCollector;
import net.doepner.sound.AudioPlayer;
import net.doepner.sound.ConvertingStreamPlayer;
import net.doepner.sound.DirectStreamPlayer;
import net.doepner.sound.StdAudioPlayer;
import net.doepner.speech.AudioFileSpeaker;
import net.doepner.speech.ESpeaker;
import net.doepner.speech.IterableSpeakers;
import net.doepner.speech.ManagedSpeakers;
import net.doepner.speech.Speaker;
import net.doepner.text.DocumentModel;
import net.doepner.text.TextListener;
import net.doepner.text.WordExtractor;
import net.doepner.text.WordProvider;
import net.doepner.ui.Editor;
import net.doepner.ui.EmailDialog;
import net.doepner.ui.FontChooser;
import net.doepner.ui.SwingEditor;
import net.doepner.ui.SwingEmailDialog;
import net.doepner.ui.text.AlphaNumStyler;
import net.doepner.ui.text.DocSwitchListener;
import net.doepner.ui.text.Documents;
import net.doepner.ui.text.TextStyler;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;

import static net.doepner.log.Log.Level.error;

/**
 * Wires up the application
 */
public final class Application {

    private final Log log;

    private final SwingFrame frame;

    public Application() {

        final LogProvider logProvider = new Slf4jLogProvider();
        log = logProvider.getLog(getClass());

        Thread.setDefaultUncaughtExceptionHandler(
                new UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        log.as(error, e);
                    }
                });

        final String appName = "Typepad";
        final Path homeDir = Paths.get(System.getProperty("user.home"));

        final PathHelper pathHelper =
                new StdPathHelper(appName, homeDir, logProvider);

        final ResourceFinder resourceFinder = new CascadingResourceFinder(
                new FileFinder(pathHelper),
                new ClasspathFinder(logProvider),
                new FileDownload(logProvider, pathHelper, new GoogleTranslateUrls()));

        final AudioPlayer audioPlayer = new StdAudioPlayer(logProvider,
                new DirectStreamPlayer(),
                new ConvertingStreamPlayer(logProvider));

        final LanguageChanger languageChanger = new CanadianDeutsch(logProvider);

        final IterableSpeakers speakers = new ManagedSpeakers(
                logProvider,
                new AudioFileSpeaker("google-translate",
                        resourceFinder, languageChanger, audioPlayer),
                new ESpeaker(languageChanger, "robbi")
        );

        final ImageCollector imageCollector = new StdImageCollector(
                new StdResourceCollector(resourceFinder), 10, logProvider);


        final Path configFile = pathHelper.findOrCreate("email.properties", PathType.FILE);
        final Emailer emailer = createEmailer(logProvider, configFile);

        /* MODEL */

        final TextBuffers buffers = new TextFiles(logProvider, pathHelper);

        final DocumentListener textStyler = new TextStyler(new AlphaNumStyler());

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

        final int docCount = 5;
        final DocumentModel documentModel = new Documents(docCount, docInitializer, buffers);

        final WordProvider wordProvider = new WordExtractor(logProvider, documentModel);

        /* VIEW */

        final EmailDialog emailDialog = new SwingEmailDialog(imageCollector);

        final JTextPane textPane = new JTextPane();
        textPane.setFont(new Font("serif", Font.PLAIN, 40));

        final Color caretColorMask = new Color(0, 50, 50);
        final Editor editor = new SwingEditor(textPane, caretColorMask);

        final Dimension frameSize = new Dimension(800, 600);
        final Dimension imageSize = new Dimension(100, 100);

        frame = new SwingFrame(logProvider,
                appName, editor, languageChanger, wordProvider, imageCollector,
                new FontChooser(textPane),
                new JScrollPane(textPane),
                imageSize, frameSize,
                new ActionDescriptions(),
                new SwitchLanguage(languageChanger),
                new SpeakWord(editor, wordProvider, speakers),
                new ResizeFont(-1, editor),
                new ResizeFont(1, editor),
                new SwitchDocument(documentModel),
                new SwitchSpeaker(speakers),
                new EmailAction(emailDialog, documentModel, emailer, speakers),
                new SpeakAll(documentModel, speakers),
                new StopAudioAction(speakers));



/*
        // You should work with UI (including installing L&F) inside Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Install WebLaF as application L&F
                WebLookAndFeel.install();
            }
        });
*/

        addListeners(speakers, textPane, documentModel);
    }

    private Emailer createEmailer(LogProvider logProvider, Path configFile) {
        try {
            return new SmtpEmailer(new SmtpConfig(configFile), logProvider);
        } catch (IOException e) {
            log.as(error, e);
            return new NoEmailer(logProvider);
        }
    }

    private static void addListeners(final Speaker speaker,
                                     JTextComponent textComponent,
                                     final DocumentModel documentModel) {

        addShutdownHook(new Runnable() {
            @Override
            public void run() {
                // to force saving of current document
                documentModel.switchDocument();
            }
        });

        documentModel.addTextListener(new TextListener() {
            @Override
            public void handleText(final String text) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        speaker.speak(text);
                    }
                }).start();
            }
        });

        documentModel.addDocSwitchListener(new DocSwitchListener() {
            @Override
            public void docSwitched(Document document) {
                textComponent.setDocument(document);
            }
        });
    }

    private static void addShutdownHook(Runnable runnable) {
        Runtime.getRuntime().addShutdownHook(new Thread(runnable));
    }


    public void start() {
        frame.show();
    }
}
