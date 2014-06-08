package net.doepner.app.typepad.ui;

import net.doepner.event.ChangeListener;
import net.doepner.event.ChangeNotifier;
import net.doepner.i18n.L10n;
import net.doepner.lang.Language;
import net.doepner.log.Log;
import net.doepner.log.LogProvider;
import net.doepner.resources.ImageCollector;
import net.doepner.text.WordProvider;
import net.doepner.ui.IAction;
import net.doepner.ui.ImageContainer;
import net.doepner.ui.SwingAction;
import net.doepner.ui.SwingEditor;
import net.doepner.ui.UiAction;
import net.doepner.ui.images.ImagePanel;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static net.doepner.log.Log.Level.debug;
import static net.doepner.log.Log.Level.info;
import static net.doepner.util.ComparisonUtil.bothNullOrEqual;
import static net.doepner.util.ComparisonUtil.not;

/**
 * Swing frame wrapper (for loose coupling)
 */
public class SwingFrame {

    private final JFrame frame;
    private final SwingEditor editor;

    private final JToolBar toolBar = new JToolBar();

    private List<ImagePanel> wordImagePanels = new LinkedList<>();
    private List<ImagePanel> charImagePanels = new LinkedList<>();

    public SwingFrame(LogProvider logProvider,
                      String appName, SwingEditor editor,
                      final ChangeNotifier<Language> languageChanger,
                      final WordProvider wordProvider,
                      final ImageCollector ic,
                      Dimension imageSize,
                      Dimension frameSize,
                      L10n<IAction, String> actionDescr,
                      IAction... actions) {
        final Log log = logProvider.getLog(getClass());
        this.editor = editor;

        final JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(editor.createScrollPane(), BorderLayout.CENTER);

        addImageBar(wrapper, charImagePanels, BoxLayout.PAGE_AXIS, BorderLayout.EAST, imageSize);
        addImageBar(wrapper, wordImagePanels, BoxLayout.LINE_AXIS, BorderLayout.SOUTH, imageSize);

        frame = new JFrame(appName);
        frame.setPreferredSize(frameSize);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.add(toolBar, BorderLayout.PAGE_START);
        frame.add(wrapper, BorderLayout.CENTER);

        int i = 0;
        for (IAction action : actions) {
            final UiAction uiAction = new SwingAction(action, actionDescr, logProvider);
            languageChanger.addListener(uiAction);
            addAction(uiAction, i);

            log.as(debug, "Added action '{}'", action);
            i++;
        }
        addOtherToolbarComponents();

        log.as(info, "All {} actions set on View", actions.length);
        log.as(info, "View constructed");

        editor.addTextPositionListener(new ChangeListener<Integer>() {
            @Override
            public void handleChange(final Integer before, final Integer after) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final Character ch = wordProvider.getCharacter(after);
                        if (not(bothNullOrEqual(ch, wordProvider.getCharacter(before)))) {
                            setImages(ic.getImages(String.valueOf(ch)), charImagePanels);

                        }
                        final String word = wordProvider.getWord(after);
                        if (not(bothNullOrEqual(word, wordProvider.getWord(before)))) {
                            setImages(ic.getImages(word), wordImagePanels);
                        }
                    }
                }).start();
            }
        });
    }

    private void setImages(Iterable<Image> images,
                           Iterable<? extends ImageContainer> panels) {
        final Iterator<Image> imageIter = images.iterator();
        for (ImageContainer panel : panels) {
            panel.setImage(imageIter.next());
        }
    }

    private void addImageBar(JPanel wrapper, List<ImagePanel> panels,
                             int axis, String constraints,
                             Dimension imageSize) {
        final JPanel imageBar = new JPanel();
        imageBar.setLayout(new BoxLayout(imageBar, axis));

        for (int i = 0; i < 3; i++) {
            final ImagePanel imagePanel = new ImagePanel();
            imagePanel.setPreferredSize(imageSize);
            imagePanel.setMaximumSize(imageSize);
            imageBar.add(imagePanel);
            panels.add(imagePanel);
        }
        wrapper.add(imageBar, constraints);
    }

    public void addAction(Action action, int i) {
        editor.addAction(action, i);
        toolBar.add(new JButton(action));
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

    public void addOtherToolbarComponents() {
        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(editor.createFontChooser());
    }

    public Component getMainComponent() {
        return frame;
    }
}
