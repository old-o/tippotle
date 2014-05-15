package net.doepner.ui;

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
import java.util.LinkedList;
import java.util.List;

/**
 * Swing frame wrapper (for loose coupling)
 */
public class SwingFrame {

    private final JFrame frame;
    private final SwingEditor editor;

    private final JToolBar toolBar = new JToolBar();

    private List<ImagePanel> wordImagePanels = new LinkedList<>();
    private List<ImagePanel> charImagePanels = new LinkedList<>();

    public SwingFrame(String appName, SwingEditor editor, Dimension imageSize,
                      Dimension frameSize) {
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

    public List<ImagePanel> getCharImagePanels() {
        return charImagePanels;
    }

    public Iterable<? extends ImageContainer> getWordImagePanels() {
        return wordImagePanels;
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

    public SwingEditor getEditor() {
        return editor;
    }

    public Component getMainComponent() {
        return frame;
    }
}
