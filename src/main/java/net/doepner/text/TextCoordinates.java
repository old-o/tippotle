package net.doepner.text;

import net.doepner.event.ChangeListener;

public interface TextCoordinates {

	int getTextPosition();

    void addTextPositionListener(ChangeListener<Integer> tpl);

}
