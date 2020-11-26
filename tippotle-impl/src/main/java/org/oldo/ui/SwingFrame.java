package org.oldo.ui;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import java.awt.Dimension;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.EAST;
import static java.awt.BorderLayout.PAGE_START;
import static java.awt.BorderLayout.SOUTH;
import static javax.swing.SwingUtilities.invokeLater;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * Swing frame wrapper (for loose coupling)
 */
public final class SwingFrame {

    private final JFrame frame;

    public SwingFrame(String appName, Dimension size,
                      JToolBar toolBar,
                      JComponent center,
                      JComponent east,
                      JComponent south) {

        frame = new JFrame(appName);
        frame.setPreferredSize(size);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        final JPanel content = new JPanel(new BorderLayout());
        content.add(center, CENTER);
        content.add(east, EAST);
        content.add(south, SOUTH);

        frame.add(toolBar, PAGE_START);
        frame.add(content, CENTER);
    }

    public void show() {
        invokeLater(() -> {
            frame.pack();
            frame.setVisible(true);
        });
    }
}
