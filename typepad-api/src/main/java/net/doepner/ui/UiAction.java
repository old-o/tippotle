package net.doepner.ui;

import net.doepner.lang.Language;

import javax.swing.Action;

/**
 * Language-aware Swing action (interface)
 */
public interface UiAction extends Action {

    void setLanguage(Language language);
}
