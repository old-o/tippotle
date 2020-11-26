package org.oldo.text;

import org.guppy4j.event.ChangeListener;

public interface TextCoordinates {

    int textPosition();

    void addTextPositionListener(ChangeListener<Integer> tpl);

}
