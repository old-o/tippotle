package net.doepner.app.typepad.ui;

import net.doepner.i18n.L10n;
import net.doepner.lang.Language;
import net.doepner.text.WordProvider;
import net.doepner.ui.Editor;
import net.doepner.ui.FontChooser;
import net.doepner.ui.IAction;
import net.doepner.ui.ImageContainer;
import net.doepner.ui.SwingAction;
import net.doepner.ui.UiAction;
import net.doepner.ui.images.ImagePanel;
import org.guppy4j.event.ChangeListener;
import org.guppy4j.event.ChangeNotifier;
import org.guppy4j.log.Log;
import org.guppy4j.log.LogProvider;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Function;

import static java.awt.BorderLayout.EAST;
import static java.awt.BorderLayout.SOUTH;
import static javax.swing.Box.createHorizontalGlue;
import static javax.swing.BoxLayout.LINE_AXIS;
import static javax.swing.BoxLayout.PAGE_AXIS;
import static net.doepner.ui.SwingUtil.doInBackground;
import static org.guppy4j.BaseUtil.bothNullOrEqual;
import static org.guppy4j.BaseUtil.not;
import static org.guppy4j.log.Log.Level.debug;
import static org.guppy4j.log.Log.Level.info;

/**
 * Swing frame wrapper (for loose coupling)
 */
public final class SwingFrame {

    private final JFrame frame;

    private final Iterable<ImagePanel> wordImagePanels;
    private final Iterable<ImagePanel> charImagePanels;

    public SwingFrame(LogProvider logProvider,
                      String appName, Editor editor,
                      final ChangeNotifier<Language> languageChanger,
                      final WordProvider wordProvider,
                      final Function<String, Iterable<Image>> imageCollector,
                      FontChooser editorFontChooser,
                      JScrollPane editorScrollPane,
                      Dimension imageSize,
                      Dimension frameSize,
                      L10n<IAction, String> actionDescr,
                      IAction... actions) {

        final Log log = logProvider.getLog(getClass());

        final JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(editorScrollPane, BorderLayout.CENTER);
        charImagePanels = addImageBar(wrapper, PAGE_AXIS, EAST, imageSize);
        wordImagePanels = addImageBar(wrapper, LINE_AXIS, SOUTH, imageSize);

        final JToolBar toolBar = new JToolBar();
        int i = 0;
        for (IAction action : actions) {
            final UiAction uiAction = new SwingAction(action, actionDescr, logProvider);
            languageChanger.addListener(uiAction);
            editor.addAction(uiAction, i);
            toolBar.add(new JButton(uiAction));

            log.as(debug, "Added action '{}'", action);
            i++;
        }
        log.as(info, "All {} actions initialized", actions.length);

        toolBar.add(createHorizontalGlue());
        toolBar.add(editorFontChooser);

        editor.addTextPositionListener(new ChangeListener<Integer>() {
            @Override
            public void handleChange(final Integer before, final Integer after) {
                doInBackground(() -> {
                    final Character ch = wordProvider.character(after);
                    if (not(bothNullOrEqual(ch, wordProvider.character(before)))) {
                        setImages(imageCollector.apply(String.valueOf(ch)), charImagePanels);
                    }
                    final String word = wordProvider.word(after);
                    if ((word == null || word.trim().length() != 1)
                            && not(bothNullOrEqual(word, wordProvider.word(before)))) {
                        setImages(imageCollector.apply(word), wordImagePanels);
                    }
                });
            }
        });

        frame = new JFrame(appName);
        frame.setPreferredSize(frameSize);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.add(toolBar, BorderLayout.PAGE_START);
        frame.add(wrapper, BorderLayout.CENTER);
    }

    private static void setImages(Iterable<Image> images,
                                  Iterable<? extends ImageContainer> panels) {
        final Iterator<Image> imageIter = images.iterator();
        for (ImageContainer panel : panels) {
            panel.setImage(imageIter.next());
        }
    }

    private static Iterable<ImagePanel> addImageBar(JPanel wrapper,
                                                    int axis, String constraints,
                                                    Dimension imageSize) {
        final JPanel imageBar = new JPanel();
        imageBar.setLayout(new BoxLayout(imageBar, axis));

        final Collection<ImagePanel> panels = new LinkedList<>();

        for (int i = 0; i < 5; i++) {
            final ImagePanel imagePanel = new ImagePanel();
            imagePanel.setPreferredSize(imageSize);
            imagePanel.setMaximumSize(imageSize);
            imageBar.add(imagePanel);
            panels.add(imagePanel);
        }
        wrapper.add(imageBar, constraints);
        return panels;
    }

    public void show() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
