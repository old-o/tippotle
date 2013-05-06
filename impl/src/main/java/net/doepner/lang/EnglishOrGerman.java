package net.doepner.lang;


import net.doepner.event.ChangeListener;
import net.doepner.event.ChangePropagator;
import net.doepner.event.ChangeSupport;

public class EnglishOrGerman implements LanguageChanger {

    private final ChangePropagator<ILanguage> propagator =
        new ChangeSupport<>();

    private boolean english;

    @Override
    public ILanguage getLanguage() {
        return english ? Language.ENGLISH : Language.DEUTSCH;
    }

    @Override
    public void changeLanguage() {
        final ILanguage before = getLanguage();
        english = !english;
        final ILanguage after = getLanguage();
        propagator.handleChange(before, after);
    }

    @Override
    public void addListener(ChangeListener<ILanguage> listener) {
        propagator.addListener(listener);
    }
}
