package org.oldo.ui.text;

import org.oldo.ui.CharStyler;
import org.oldo.util.IntRange;

import javax.swing.text.AttributeSet;
import java.awt.Color;

/**
 * Rotating color
 */
public final class ColorRotation implements CharStyler {

    private final IntRange red = new IntRange(0, 255, 0, 13);
    private final IntRange green = new IntRange(0, 255, 120, 17);
    private final IntRange blue = new IntRange(0, 255, 240, 11);

    @Override
    public AttributeSet getAttribs(char c) {
        final Color color = new Color(red.nextValue(), green.nextValue(), blue.nextValue());
        return StyleAttributes.forColor(color);
    }

}
