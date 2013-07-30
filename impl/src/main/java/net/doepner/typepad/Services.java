package net.doepner.typepad;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import net.doepner.file.PathHelper;
import net.doepner.file.StdPathHelper;
import net.doepner.file.TextBuffers;
import net.doepner.file.TextFiles;
import net.doepner.log.Log;
import net.doepner.log.StdLog;
import net.doepner.speech.AudioFileSpeaker;
import net.doepner.speech.ESpeaker;
import net.doepner.speech.SelectableSpeaker;
import net.doepner.speech.Speaker;
import net.doepner.ui.Images;
import net.doepner.ui.images.ImageHelper;

/**
 * Application services
 */
public class Services implements IServices {

    private final Log log = new StdLog();

    private final SelectableSpeaker speaker;
    private final Images images;
    private final TextBuffers buffers;

    Services(IContext context) {
        final PathHelper pathHelper = new StdPathHelper(context.getAppName(), log);
        speaker = createSpeaker(context, pathHelper);
        images = new ImageHelper(pathHelper);
        buffers = new TextFiles(pathHelper);
    }

    private SelectableSpeaker createSpeaker(IContext context, PathHelper pathHelper) {
        final List<Speaker> speakers = new LinkedList<>();
        speakers.add(new AudioFileSpeaker(pathHelper));
        try {
            speakers.add(new ESpeaker(context.getLanguageChanger()));
        } catch (IOException e) {
            log.error(e);
        }
        return new SelectableSpeaker(speakers);
    }

    @Override
    public Log getLog() {
        return log;
    }

    @Override
    public Speaker getSpeaker() {
        return speaker;
    }

    @Override
    public void switchSpeaker() {
        speaker.nextSpeaker();
    }

    @Override
    public Images getImages() {
        return images;
    }

    @Override
    public void loadBuffer(IModel model) {
        model.setText(buffers.load(model.getCurrentBuffer()).trim());
    }

    @Override
    public void saveBuffer(IModel model) {
        buffers.save(model.getText().trim(), model.getCurrentBuffer());
    }

    private Speaker createSpeaker(IContext context) {
        try {
            return new ESpeaker(context.getLanguageChanger());

        } catch (IOException e) {
            log.error(e);
            log.info("We'll use a dummy 'speaker' that just logs the output");

            return new Speaker() {
                @Override
                public void speak(String text) {
                    log.info("Speak: " + text);
                }
            };
        }
    }
}
