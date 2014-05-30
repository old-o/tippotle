package net.doepner.app.typepad;

import net.doepner.mail.Emailer;
import net.doepner.resources.ImageCollector;
import net.doepner.resources.ResourceFinder;
import net.doepner.speech.Speaker;

/**
 * Application services interface
 */
public interface IServices {

    ResourceFinder getResourceFinder();

    ImageCollector getImageCollector();

    Speaker getSpeaker();

    void switchSpeaker();

    void saveBuffer(IModel model);

    void loadBuffer(IModel model);

    Emailer getEmailer();
}
