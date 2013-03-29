package net.doepner.ui;

import java.awt.Font;

/**
 * Something that has a mutable font
 */
public interface HasFont {

    Font getFont();

    void setFont(Font font);
}
