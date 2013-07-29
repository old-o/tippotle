package net.doepner.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import net.doepner.ui.images.ImagePanel;

/**
 * Swing frame wrapper (for loose coupling)
 */
public class SwingFrame {

    private final JFrame frame;
    private final SwingEditor editor;

    private final JToolBar toolBar = new JToolBar();
    private ImagePanel imagePanel = new ImagePanel();

    public SwingFrame(String appName, SwingEditor editor, Dimension imageSize,
                      Dimension frameSize) {
        this.editor = editor;

        final JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(editor.createScrollPane(), BorderLayout.CENTER);

        final JPanel statusBar = new JPanel();
        wrapper.add(statusBar, BorderLayout.SOUTH);

        imagePanel.setPreferredSize(imageSize);
        statusBar.add(imagePanel);

        frame = new JFrame(appName);
        frame.setPreferredSize(frameSize);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.add(toolBar, BorderLayout.PAGE_START);
        frame.add(wrapper, BorderLayout.CENTER);
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

    public ImageContainer getImagePanel() {
        return imagePanel;
    }

    public void addOtherToolbarComponents() {
        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(editor.createFontChooser());
    }

    public SwingEditor getEditor() {
        return editor;
    }
}
