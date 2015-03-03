package net.doepner.ui;

import net.doepner.lang.Language;
import org.guppy4j.event.ChangeListener;

import javax.swing.Action;

/**
 * Language-aware Swing action (interface)
 */
public interface UiAction extends Action, ChangeListener<Language> {

    // no additional methods
}
