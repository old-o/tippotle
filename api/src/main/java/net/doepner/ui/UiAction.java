package net.doepner.ui;

import javax.swing.Action;

import net.doepner.lang.ILanguage;

/**
 * Language-aware Swing action (interface)
 */
public interface UiAction extends Action {

    void setLanguage(ILanguage language);
}
