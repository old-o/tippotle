package net.doepner.ui.text;

import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import java.awt.Color;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static javax.swing.text.StyleConstants.setForeground;

/**
 * Cached style attribute sets
 */
public final class StyleAttributes {

    private static final ConcurrentMap<Color, AttributeSet> cache = new ConcurrentHashMap<>();

    public static AttributeSet forColor(Color color) {
        final AttributeSet attributeSet = cache.get(color);
        if (attributeSet != null) {
            return attributeSet;
        } else {
            final MutableAttributeSet attribs = new SimpleAttributeSet();
            setForeground(attribs, color);
            cache.put(color, attribs);
            return attribs;
        }

    }
}
