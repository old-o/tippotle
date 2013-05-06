package net.doepner.typepad;

import net.doepner.file.FileHelper;
import net.doepner.file.IFileHelper;
import net.doepner.file.ImageFiles;
import net.doepner.file.TextBuffers;
import net.doepner.file.TextFiles;
import net.doepner.speech.Speaker;
import net.doepner.ui.ImageMap;
import net.doepner.ui.images.ImageHelper;

/**
 * Application services
 */
public class Services implements IServices {

    private final Speaker speaker;
    private final ImageMap imageMap;
    private final TextBuffers buffers;

    Services(IContext context) {
        this.speaker = context.getSpeaker();

        final IFileHelper fileHelper = new FileHelper(context.getAppName());
        imageMap = new ImageHelper(new ImageFiles(fileHelper));
        buffers = new TextFiles(fileHelper);
    }

    @Override
    public Speaker getSpeaker() {
        return speaker;
    }

    @Override
    public ImageMap getImageMap() {
        return imageMap;
    }

    @Override
    public void loadBuffer(IModel model) {
        model.setText(buffers.load(model.getCurrentBuffer()).trim());
    }

    @Override
    public void saveBuffer(IModel model) {
        buffers.save(model.getText().trim(), model.getCurrentBuffer());
    }
}
