package net.doepner.ui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import net.doepner.file.FileHelper;
import net.doepner.text.TextModel;
import net.doepner.ui.Icons;

public class SwitchBufferAction extends AbstractAction {

	private final FileHelper helper = new FileHelper();
	
	private final int max;
	private final TextModel textModel;
	
	private int i = 1;
	
	public SwitchBufferAction(int max, TextModel textModel) {
		this.max = max;
		this.textModel = textModel;
        putValue(SHORT_DESCRIPTION, "Switch buffer");
        Icons.setLargeIcon(this, "buffers");
		loadText();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		helper.save(textModel.getText(), i);
		i = i % max + 1;
		loadText();
	}
	
	private void loadText() {
		textModel.setText(helper.load(i));
	}
}
