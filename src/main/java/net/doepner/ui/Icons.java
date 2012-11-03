package net.doepner.ui;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.net.URL;

public class Icons {
	
	public static enum Category {
		DEVELOPMENT,
		GENERAL, 
		MEDIA,
		NAVIGATION,
		TABLE,
		TEXT,
		FLAGS;
		
		public String toString() {
			return name().toLowerCase();
		}
	}

	public static void setLargeIcon(Action action, Category cat, String name) {
        action.putValue(Action.LARGE_ICON_KEY,
                        new ImageIcon(findImage(cat, name)));
	}

    private static URL findImage(Category cat, String name) {
        final URL png = findResource(cat, name, "png");
        if (png != null) {
            return png;
        } else {
            return findResource(cat, name, "gif");
        }
    }

    private static URL findResource(Category cat, String baseName,
                                    String extension) {
        return Icon.class.getResource(
                "/icons/" + cat + "/" + baseName + "24." + extension);
    }

}
