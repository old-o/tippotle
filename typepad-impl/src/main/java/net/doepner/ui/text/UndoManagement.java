package net.doepner.ui.text;

import org.guppy4j.log.Log;
import org.guppy4j.log.LogProvider;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.event.DocumentEvent;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AbstractDocument.DefaultDocumentEvent;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;
import java.awt.event.ActionEvent;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static javax.swing.KeyStroke.getKeyStroke;
import static javax.swing.event.DocumentEvent.EventType.CHANGE;
import static org.guppy4j.BaseUtil.not;
import static org.guppy4j.log.Log.Level.error;
import static org.guppy4j.log.Log.Level.info;

/**
 * Manages undo/redo operation
 */
public final class UndoManagement implements DocSwitchListener {

    private static final String UNDO = "Undo";
    private static final String REDO = "Redo";

    private final Map<Document, UndoManager> undoManagers = new ConcurrentHashMap<>();

    /**
     * Listen for undo and redo events
     */
    private final UndoableEditListener listener = new UndoableEditListener() {
        @Override
        public void undoableEditHappened(UndoableEditEvent e) {
            final UndoableEdit edit = e.getEdit();
            if (edit instanceof DefaultDocumentEvent
                    && ((DocumentEvent) edit).getType() == CHANGE) {
                // ignore style attribute CHANGE events
                return;
            }
            // otherwise it is an INSERT or REMOVE and we track it:
            final Document document = (Document) e.getSource();
            final UndoManager manager = undoManagers.get(document);
            if (manager != null) {
                manager.addEdit(edit);
            }
        }
    };

    private final Log log;

    public UndoManagement(LogProvider logProvider,
                          JTextComponent textComponent) {
        log = logProvider.getLog(getClass());

        final ActionMap actionMap = textComponent.getActionMap();
        // Create an undo action and add it to the text component
        actionMap.put(UNDO,
                new AbstractAction(UNDO) {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        final Document document = textComponent.getDocument();
                        final UndoManager manager = undoManagers.get(document);
                        try {
                            if (manager != null && manager.canUndo()) {
                                manager.undo();
                                log.as(info, "Performed UNDO on document {}", document);
                            }
                        } catch (CannotUndoException cue) {
                            log.as(error, cue);
                        }
                    }
                });
        // Create a redo action and add it to the text component
        actionMap.put(REDO,
                new AbstractAction(REDO) {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        final Document document = textComponent.getDocument();
                        final UndoManager manager = undoManagers.get(document);
                        try {
                            if (manager != null && manager.canRedo()) {
                                manager.redo();
                                log.as(info, "Performed REDO on document {}", document);
                            }
                        } catch (CannotRedoException cre) {
                            log.as(error, cre);
                        }
                    }
                });

        final InputMap inputMap = textComponent.getInputMap();
        inputMap.put(getKeyStroke("Control Z"), UNDO);
        inputMap.put(getKeyStroke("Control Y"), REDO);
    }

    @Override
    public void docSwitched(Document document) {
        if (not(undoManagers.containsKey(document))) {
            undoManagers.put(document, new UndoManager());
            document.addUndoableEditListener(listener);
        }
    }
}
