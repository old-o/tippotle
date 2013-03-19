package net.doepner.ui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.doepner.file.TextBuffers;
import net.doepner.text.TextProvider;
import net.doepner.text.TextReceiver;

public class SwitchBuffer extends AbstractAction implements IdAction {

	private final TextBuffers textBuffers;
	
	private final int max;

	private final TextProvider textProvider;
    private final TextReceiver textReceiver;
	
	private int i = 1;
	
	public SwitchBuffer(int max,
                        TextProvider textProvider,
                        TextReceiver textReceiver,
                        TextBuffers textBuffers) {
		this.max = max;
		this.textProvider = textProvider;
        this.textReceiver = textReceiver;
        this.textBuffers = textBuffers;
		loadText();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		textBuffers.save(textProvider.getText(), i);
		i = i % max + 1;
		loadText();
	}
	
	private void loadText() {
		textReceiver.setText(textBuffers.load(i));
	}

    @Override
    public ActionId getId() {
        return ActionId.SWITCH_BUFFER;
    }
}
