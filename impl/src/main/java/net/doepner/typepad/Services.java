package net.doepner.typepad;

import java.io.IOException;

import net.doepner.file.PathHelper;
import net.doepner.file.StdPathHelper;
import net.doepner.file.TextBuffers;
import net.doepner.file.TextFiles;
import net.doepner.log.Log;
import net.doepner.log.StdLog;
import net.doepner.speech.ESpeaker;
import net.doepner.speech.Speaker;
import net.doepner.ui.Images;
import net.doepner.ui.images.ImageHelper;

/**
 * Application services
 */
public class Services implements IServices {

    private final Log log = new StdLog();

    private final Speaker speaker;
    private final Images images;
    private final TextBuffers buffers;

    Services(IContext context) {
        final PathHelper pathHelper = new StdPathHelper(context.getAppName(), log);
        speaker = createSpeaker(context);

        // TODO: Make speaker selectable by user
        // speaker = new AudioFileSpeaker(pathHelper);

        images = new ImageHelper(pathHelper);
        buffers = new TextFiles(pathHelper);
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
