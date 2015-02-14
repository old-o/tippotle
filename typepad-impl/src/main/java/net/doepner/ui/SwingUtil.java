package net.doepner.ui;

import javax.swing.SwingWorker;

/**
 * TODO: Document this!
 */
public class SwingUtil {

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
