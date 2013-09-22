package net.doepner.lang;


import net.doepner.event.ChangeListener;
import net.doepner.event.ChangePropagator;
import net.doepner.event.ChangeSupport;

public class CanadianDeutsch implements LanguageChanger {

    private final ChangePropagator<Language> propagator = new ChangeSupport<>();

    private boolean deutsch;

    @Override
    public Language getLanguage() {
        return deutsch ? LanguageEnum.DEUTSCH : LanguageEnum.CANADIAN;
    }

    @Override
    public void changeLanguage() {
        final Language before = getLanguage();
        deutsch = !deutsch;
        final Language after = getLanguage();
        propagator.handleChange(before, after);
    }

    @Override
    public void addListener(ChangeListener<Language> listener) {
        propagator.addListener(listener);
    }
}
