package net.doepner.text;

public interface TextModel extends TextProvider, TextReceiver {

    void addTextListener(TextListener listener);

}
