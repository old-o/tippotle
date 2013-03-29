package net.doepner.app;

import javax.swing.text.StyledDocument;

import net.doepner.app.api.IContext;
import net.doepner.app.api.IModel;
import net.doepner.app.api.IView;
import net.doepner.ui.text.DocTextModel;

import static net.doepner.app.DocUtil.createDocument;

/**
 * The application itself (mainly an abstract MVC container)
 */
public class Application {

    private final IView view;

    public Application(final IContext context) {
        final StyledDocument doc = createDocument(context);

        final IModel model = new Model(new DocTextModel(doc), context);

        view = new View(context.getAppName(), doc);

        new Controller(model, view, new Services(context));
    }

    void run() {
        view.show();
    }
}
