package net.doepner.text;

import org.guppy4j.event.ChangeListener;

public interface TextCoordinates {

    int getTextPosition();

    void addTextPositionListener(ChangeListener<Integer> tpl);

}
