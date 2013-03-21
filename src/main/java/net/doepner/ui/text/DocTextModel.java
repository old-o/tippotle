package net.doepner.ui.text;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import net.doepner.text.TextModel;

public class DocTextModel implements TextModel {

    private final Document doc;

    public DocTextModel(Document doc) {
        this.doc = doc;
    }

    @Override
    public void setText(String text) {
        try {
            doc.remove(0, doc.getLength());
            doc.insertString(0, text, null);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getText() {
        try {
            return doc.getText(0, doc.getLength());
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }

    }
}
