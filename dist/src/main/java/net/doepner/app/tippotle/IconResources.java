package net.doepner.app.tippotle;

import net.doepner.app.tippotle.action.ActionEnum;
import net.doepner.lang.LanguageEnum;
import net.doepner.ui.Icons;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Maps icon keys to cached icons
 */
public final class IconResources implements Icons {

    private final Map<Object, Icon> iconMap = new HashMap<>();

    public IconResources() {
        map(LanguageEnum.CANADIAN, "/net/doepner/ui/en-ca.png");
        map(LanguageEnum.DEUTSCH, "/net/doepner/ui/de.png");

        map(ActionEnum.SWITCH_DOCUMENT, "/net/doepner/ui/switch-document.png");
        map(ActionEnum.BIGGER_FONT, "/net/doepner/ui/zoom-in.png");
        map(ActionEnum.SMALLER_FONT, "/net/doepner/ui/zoom-out.png");
        map(ActionEnum.SPEAK_WORD, "/net/doepner/ui/speak-word.png");
        map(ActionEnum.SPEAK_ALL, "/net/doepner/ui/speak-all.png");
        map(ActionEnum.STOP_AUDIO, "/net/doepner/ui/stop-audio.png");
        map(ActionEnum.EMAIL, "/net/doepner/ui/email.png");

        map(Application.ESPEAKER_NAME, "/net/doepner/ui/robbi.png");
        map(Application.GOOGLE_SPEAKER_NAME, "/net/doepner/ui/google-translate.png");
    }

    @Override
    public Icon get(Object key) {
        return iconMap.get(key);
    }

    private void map(Object o, String iconLocation) {
        iconMap.put(o, getIcon(iconLocation));
    }

    private Icon getIcon(String iconLocation) {
        if (iconLocation.isEmpty()) {
            throw new IllegalStateException("Icon location unspecified");
        }
        final URL url = getClass().getResource(iconLocation);
        if (url == null) {
            throw new IllegalStateException("Missing resource: " + iconLocation);
        } else {
            return new ImageIcon(url);
        }
    }
}
