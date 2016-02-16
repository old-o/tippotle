package net.doepner.app.tippotle.ui;

import net.doepner.ui.Action;
import net.doepner.ui.FontChooser;
import org.guppy4j.log.Log;
import org.guppy4j.log.LogProvider;

import javax.swing.JButton;
import javax.swing.JToolBar;
import java.util.function.Supplier;

import static javax.swing.Box.createHorizontalGlue;
import static org.guppy4j.log.Log.Level.debug;
import static org.guppy4j.log.Log.Level.info;

/**
 * Supplies a toolbar
 */
public final class EditorToolBar implements Supplier<JToolBar> {

    private final JToolBar toolBar = new JToolBar();

    public EditorToolBar(
            LogProvider logProvider,
            FontChooser editorFontChooser,
            ActionConverter uiActions,
            Action... actions) {

        final Log log = logProvider.getLog(getClass());

        for (Action action : actions) {
            toolBar.add(new JButton(uiActions.from(action)));
            log.as(debug, "Added action '{}'", action);
        }
        log.as(info, "All {} actions initialized", actions.length);

        toolBar.add(createHorizontalGlue());
        toolBar.add(editorFontChooser);
    }


    @Override
    public JToolBar get() {
        return toolBar;
    }
}
