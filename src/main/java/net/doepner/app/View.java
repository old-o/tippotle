package net.doepner.app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.StyledDocument;

import net.doepner.app.api.IView;
import net.doepner.event.ChangeListener;
import net.doepner.ui.action.IdAction;
import net.doepner.ui.icons.IconLoader;
import net.doepner.ui.images.ImageContainer;
import net.doepner.ui.images.ImagePanel;
import net.doepner.ui.text.FontChooser;

import static javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW;
import static javax.swing.KeyStroke.getKeyStroke;

public class View implements IView {

    private final JFrame frame;
    private final ImageContainer imageContainer;

    private final JTextPane editor;
    private final JToolBar toolBar = new JToolBar();

    View(String appName, StyledDocument doc) {
        frame = new JFrame(appName);

        editor = new JTextPane(doc);
        editor.setFont(new Font("serif", Font.PLAIN, 40));

        toolBar.setFloatable(false);

        // TODO:
        // frame.setLayout(new MigLayout());

        frame.setPreferredSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(toolBar, BorderLayout.PAGE_START);

        final JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(new JScrollPane(editor), BorderLayout.CENTER);

        final JPanel statusBar = new JPanel();
        wrapper.add(statusBar, BorderLayout.SOUTH);

        final ImagePanel imagePanel = new ImagePanel();
        imagePanel.setPreferredSize(new Dimension(100, 100));
        statusBar.add(imagePanel);

        frame.add(wrapper, BorderLayout.CENTER);

        imageContainer = imagePanel;
    }

    @Override
    public void setActions(Iterable<IdAction> actions) {
        final InputMap inputMap = editor.getInputMap(WHEN_IN_FOCUSED_WINDOW);
        final ActionMap actionMap = editor.getActionMap();

        int i = 0;
        for (IdAction action : actions) {
            setIcon(action);

            inputMap.put(getKeyStroke(KeyEvent.VK_F1 + i, 0), i);
            action.putValue(Action.NAME, "F" + (i + 1));
            actionMap.put(i, action);
            i++;

            toolBar.add(new JButton(action));
        }

        toolBar.add(Box.createHorizontalGlue());
        toolBar.add(new FontChooser(editor));
    }

    private void setIcon(IdAction action) {
        if (action.getValue(Action.LARGE_ICON_KEY) == null) {
            final Icon icon = IconLoader.getIcon(action.getId());
            action.putValue(Action.LARGE_ICON_KEY, icon);
        }
    }

    @Override
    public void show() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    @Override
    public void showImage(Image image) {
        imageContainer.setImage(image);
    }

    @Override
    public void addTextPositionListener(final ChangeListener<Integer> tpl) {
        editor.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                tpl.handleChange(null, e.getDot());
            }
        });
    }

    @Override
    public int getTextPosition() {
        return editor.getCaretPosition();
    }

    @Override
    public Font getFont() {
        return editor.getFont();
    }

    @Override
    public void setFont(Font font) {
        editor.setFont(font);
    }
}
