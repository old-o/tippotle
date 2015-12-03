package net.doepner.ui.text;

import net.doepner.file.TextBuffers;
import net.doepner.text.DocumentModel;
import net.doepner.text.TextListener;
import org.guppy4j.log.LogProvider;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

public final class Documents implements DocumentModel {

    private final Document[] docs;
    private final TextBuffers buffers;
    private final LogProvider logProvider;

    private int index;

    private final Collection<DocSwitchListener> docSwitchListeners = new ArrayList<>();

    public Documents(int docCount,
                     Function<String, Document> docInitializer,
                     TextBuffers buffers,
                     LogProvider logProvider) {
        this.logProvider = logProvider;
        docs = new Document[docCount];
        for (int i = 0; i < docCount; i++) {
            docs[i] = docInitializer.apply(buffers.load(i + 1));
        }
        this.buffers = buffers;
        index = 0;
    }

    @Override
    public String getText() {
        final Document doc = doc();
        try {
            return doc.getText(0, doc.getLength());
        } catch (BadLocationException e) {
            throw new IllegalStateException(e);
        }
    }

    private Document doc() {
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
        docSwitchListener.docSwitched(doc());
    }

    @Override
    public void switchDocument() {
        buffers.save(getText(), index + 1);
        index = (index + 1) % docs.length;
        notifyListeners();
    }

    private void notifyListeners() {
        docSwitchListeners.forEach(l -> l.docSwitched(doc()));
    }
}
