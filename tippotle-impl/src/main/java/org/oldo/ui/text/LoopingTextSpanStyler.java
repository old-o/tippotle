package org.oldo.ui.text;

import org.oldo.text.DocumentModel;
import org.oldo.text.TextSpan;
import org.oldo.ui.CharStyler;

import javax.swing.Timer;
import java.util.function.Consumer;

/**
 * Starts a Swing timer to repeatedly apply a text span style change
 */
public final class LoopingTextSpanStyler implements Consumer<TextSpan> {

    private final DocumentModel documentModel;
    private final CharStyler charStyler;
    private final int delayMillis;

    private Timer timer;

    public LoopingTextSpanStyler(DocumentModel documentModel, CharStyler charStyler, int delayMillis) {
        this.documentModel = documentModel;
        this.charStyler = charStyler;
        this.delayMillis = delayMillis;
    }

    @Override
    public void accept(TextSpan textSpan) {
        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(delayMillis, e -> documentModel.applyStyle(charStyler, textSpan));
        timer.start();
    }
}
