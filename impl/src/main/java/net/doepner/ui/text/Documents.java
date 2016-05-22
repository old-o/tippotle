package net.doepner.ui.text;

import net.doepner.file.TextBuffers;
import net.doepner.text.DocumentModel;
import net.doepner.text.TextListener;
import net.doepner.text.TextSpan;
import net.doepner.ui.CharStyler;
import org.guppy4j.log.LogProvider;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

public final class Documents implements DocumentModel {

    private final StyledDocument[] docs;
    private final TextBuffers buffers;
    private final LogProvider logProvider;

    private int index;

    private final Collection<DocSwitchListener> docSwitchListeners = new ArrayList<>();

    public Documents(int docCount,
                     Function<String, StyledDocument> docInitializer,
                     TextBuffers buffers,
                     LogProvider logProvider) {
        this.logProvider = logProvider;
        docs = new StyledDocument[docCount];
        for (int i = 0; i < docCount; i++) {
            docs[i] = docInitializer.apply(buffers.load(i + 1));
        }
        this.buffers = buffers;
        index = 0;
    }

    @Override
    public String getText() {
        final StyledDocument doc = getDoc();
        try {
            return doc.getText(0, doc.getLength());
        } catch (BadLocationException e) {
            throw new IllegalStateException(e);
        }
    }

    private StyledDocument getDoc() {
        return docs[index];
    }

    @Override
    public void addTextListener(final TextListener listener) {
        final DocumentListener docUpdateAdapter = new DocUpdateAdapter() {
            @Override
            protected void handleUpdate(DocumentEvent event) {
                final DocEvent docEvent = new DocEvent(logProvider, event);
                listener.handleText(docEvent.getText());
            }
        };
        for (Document doc : docs) {
            doc.addDocumentListener(docUpdateAdapter);
        }
    }

    @Override
    public void addDocSwitchListener(DocSwitchListener docSwitchListener) {
        docSwitchListeners.add(docSwitchListener);
        // Synthesize event so listener knows current document
        docSwitchListener.docSwitched(getDoc());
    }

    @Override
    public void applyStyle(CharStyler charStyler, TextSpan textSpan) {
        final AttributeSet attribs = charStyler.getAttribs(' ');
        getDoc().setCharacterAttributes(textSpan.getStart(), textSpan.getContent().length(), attribs, true);
    }

    @Override
    public void switchDocument() {
        buffers.save(getText(), index + 1);
        index = (index + 1) % docs.length;
        notifyListeners();
    }

    private void notifyListeners() {
        docSwitchListeners.forEach(l -> l.docSwitched(getDoc()));
    }
}
