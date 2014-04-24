package net.doepner.app.typepad;

import net.doepner.lang.CanadianDeutsch;
import net.doepner.log.Slf4jLogProvider;

/**
 * Main class with main method (entry point for app execution)
 */
public class Main {

    public static void main(String[] args) {

        final Context context = new Context("Typepad",
            new CanadianDeutsch(),
            new Slf4jLogProvider());

        new Application(context).run();
    }
}
