package net.doepner.ui;

import java.io.Serializable;

/**
 * Computes caret width
 */
public interface CaretContext extends Serializable {

    /**
     * @return The caret width
     */
    int getCaretWidth();
}
