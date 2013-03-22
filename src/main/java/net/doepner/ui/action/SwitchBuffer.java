package net.doepner.ui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.doepner.file.TextBuffers;
import net.doepner.text.TextModel;

public class SwitchBuffer extends AbstractAction implements IdAction {

    private final TextBuffers textBuffers;
    private final TextModel textModel;

    private final int max;
    private int i = 1;

    public SwitchBuffer(int max, TextModel textModel, TextBuffers textBuffers) {
        this.max = max;
        this.textModel = textModel;
        this.textBuffers = textBuffers;
        loadText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        textBuffers.save(textModel.getText(), i);
        i = i % max + 1;
        loadText();
    }

    private void loadText() {
        textModel.setText(textBuffers.load(i));
    }

    @Override
    public ActionId getId() {
        return ActionId.SWITCH_BUFFER;
    }
}
