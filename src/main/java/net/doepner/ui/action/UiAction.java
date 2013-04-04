package net.doepner.ui.action;

import javax.swing.Action;

import net.doepner.lang.Language;

/**
 * Language-aware Swing action (interface)
 */
public interface UiAction extends Action {

    void setLanguage(Language language);
}
