package net.doepner.app.typepad.action;

import net.doepner.file.TextBuffers;
import net.doepner.log.LogProvider;
import net.doepner.text.TextModel;
import net.doepner.ui.IAction;

public class SwitchBuffer implements IAction {

    private final TextModel model;
    private final TextBuffers buffers;

    public SwitchBuffer(LogProvider logProvider,
                        TextModel model, TextBuffers buffers) {
        this.model = model;
        this.buffers = buffers;
        loadBuffer();
    }

    @Override
    public void execute() {
        saveBuffer();
        buffers.nextBuffer();
        loadBuffer();
    }

    @Override
    public ActionEnum getId() {
        return ActionEnum.SWITCH_BUFFER;
    }

    private void loadBuffer() {
        model.setText(buffers.load().trim());

    }

    private void saveBuffer() {
        buffers.save(model.getText().trim());
    }
}
