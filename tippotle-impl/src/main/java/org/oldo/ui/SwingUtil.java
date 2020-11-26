package org.oldo.ui;

import javax.swing.SwingWorker;

/**
 * Swing utility methods
 */
public final class SwingUtil {

    public static void doInBackground(final Runnable r) {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                r.run();
                return null;
            }
        }.execute();
    }
}
