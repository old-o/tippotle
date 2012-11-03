package net.doepner.ui;

import javax.swing.Action;
import javax.swing.ImageIcon;

public class Icons {

	public static void setLargeIcon(Action action, String name) {
        action.putValue(Action.LARGE_ICON_KEY, new ImageIcon(
                Icons.class.getResource("icons/" + name + ".png")));
	}
}
