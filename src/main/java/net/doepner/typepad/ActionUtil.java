package net.doepner.typepad;

import java.awt.event.KeyEvent;

import javax.swing.Icon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;

import net.doepner.ui.action.Action;
import net.doepner.ui.action.ActionId;
import net.doepner.ui.icons.IconLoader;

/**
 * Utility class for settig up action objects
 */
public class ActionUtil {

    static void setIcon(Action action) {
        final String fileName = getIconFileName(action.getId());
        final Icon icon = IconLoader.load(fileName);
        if (icon != null) {
            action.putValue(Action.LARGE_ICON_KEY, icon);
        }
    }

    private static String getIconFileName(ActionId id) {
        switch (id) {
            case BIGGER_FONT:
                return "zoom-in.png";
            case SMALLER_FONT:
                return "zoom-out.png";
            case SPEAK_WORD:
                return "volume.png";
            case SWITCH_BUFFER:
                return "buffers.png";
            case SWITCH_LANGUAGE:
                return "languages.png";
        }
        throw new IllegalArgumentException("Unknown action id: " + id);
    }

    static void mapFunctionKeys(JTextPane pane, Iterable<Action> actions) {
		final InputMap inputMap = pane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        Integer i = 0;
		for (Action action : actions) {
			inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1 + i, 0), i);
			action.putValue(Action.NAME, "F" + (i+1));
			pane.getActionMap().put(i, action);
            i++;
		}
	}
}
