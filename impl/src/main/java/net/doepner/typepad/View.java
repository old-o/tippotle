package net.doepner.typepad;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.Icon;

import net.doepner.i18n.L10n;
import net.doepner.lang.ILanguage;
import net.doepner.typepad.action.ActionDescriptions;
import net.doepner.typepad.action.SwingAction;
import net.doepner.ui.ActionId;
import net.doepner.ui.Editor;
import net.doepner.ui.IAction;
import net.doepner.ui.IconLoader;
import net.doepner.ui.SwingEditor;
import net.doepner.ui.SwingFrame;
import net.doepner.ui.UiAction;

public class View implements IView {

    private final SwingFrame frame;

    private final Collection<UiAction> uiActions = new LinkedList<>();

    private final L10n<ActionId, String> actionDescr = new ActionDescriptions();
    private final L10n<ActionId, Icon> iconLoader = new IconLoader();

    View(String appName) {
        final SwingEditor editor = new SwingEditor(new Font("serif", Font.PLAIN, 40));

        final Dimension frameSize = new Dimension(800, 600);
        final Dimension imageSize = new Dimension(100, 100);

        frame = new SwingFrame(appName, editor, imageSize, frameSize);
    }

    @Override
    public void setActions(Iterable<IAction> actions) {
        int i = 0;
        for (IAction action : actions) {
            final UiAction uiAction = new SwingAction(action, iconLoader, actionDescr);
            frame.addAction(uiAction, i);
            uiActions.add(uiAction);
            i++;
        }
        frame.addOtherToolbarComponents();
    }

    @Override
    public void setLanguage(ILanguage language) {
        for (UiAction uiAction : uiActions) {
            uiAction.setLanguage(language);
        }
    }

    @Override
    public Editor getEditor() {
        return frame.getEditor();
    }

    @Override
    public void show() {
        frame.show();
    }

    @Override
    public void showImage(Image image) {
        frame.getImagePanel().setImage(image);
    }
}
