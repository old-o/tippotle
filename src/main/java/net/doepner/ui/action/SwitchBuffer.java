package net.doepner.ui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.doepner.file.FileHelper;
import net.doepner.text.TextProvider;
import net.doepner.text.TextReceiver;

public class SwitchBuffer extends AbstractAction implements Action {

	private final FileHelper helper = new FileHelper();
	
	private final int max;

	private final TextProvider textProvider;
    private final TextReceiver textReceiver;
	
	private int i = 1;
	
	public SwitchBuffer(int max,
                        TextProvider textProvider,
                        TextReceiver textReceiver) {
		this.max = max;
		this.textProvider = textProvider;
        this.textReceiver = textReceiver;
		loadText();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		helper.save(textProvider.getText(), i);
		i = i % max + 1;
		loadText();
	}
	
	private void loadText() {
		textReceiver.setText(helper.load(i));
	}

    @Override
    public ActionId getId() {
        return ActionId.SWITCH_BUFFER;
    }
}
