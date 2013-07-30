package net.doepner.typepad;

import net.doepner.log.Log;
import net.doepner.speech.Speaker;
import net.doepner.ui.Images;

/**
 * Application services interface
 */
public interface IServices {

    Log getLog();

    Speaker getSpeaker();

    void switchSpeaker();

    Images getImages();

    void saveBuffer(IModel model);

    void loadBuffer(IModel model);
}
