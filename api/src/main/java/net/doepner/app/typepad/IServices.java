package net.doepner.app.typepad;

import net.doepner.speech.Speaker;
import net.doepner.ui.Images;

/**
 * Application services interface
 */
public interface IServices {

    Speaker getSpeaker();

    void switchSpeaker();

    Images getImages();

    void saveBuffer(IModel model);

    void loadBuffer(IModel model);
}
