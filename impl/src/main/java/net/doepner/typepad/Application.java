package net.doepner.typepad;

import javax.swing.text.StyledDocument;

import net.doepner.ui.text.DocTextModel;

/**
 * The application itself (mainly an abstract MVC container)
 */
public class Application {

    private final IView view;

    public Application(final IContext context) {
        final StyledDocument doc = DocUtil.createDocument(context);

        final IModel model = new Model(new DocTextModel(doc), context);

        view = new View(context.getAppName(), doc);

        new Controller(model, view, new Services(context), context.getLog());
    }

    void run() {
        view.show();
    }
}
