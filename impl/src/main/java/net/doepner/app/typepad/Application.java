package net.doepner.app.typepad;

/**
 * The application itself (mainly an abstract MVC container)
 */
public class Application {

    private final IView view;

    public Application(final IContext context) {
        final Services services = new Services(context);

        view = new View(context.getAppName(), services.getLog());

        final IModel model = new Model(view.getEditor().getTextModel(), context);

        new Controller(model, view, services);
    }

    void run() {
        view.show();
    }
}
