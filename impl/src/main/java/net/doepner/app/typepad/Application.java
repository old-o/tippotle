package net.doepner.app.typepad;

/**
 * The application itself (mainly an abstract MVC container)
 */
public class Application {

    private final IView view;

    public Application(final IContext context) {

        view = new View(context.getAppName(), context);

        final IModel model = new Model(view.getEditor().getTextModel(), context);

        new Controller(model, view, new Services(context), context);
    }

    void run() {
        view.show();
    }
}
