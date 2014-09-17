package net.doepner.lang;


import net.doepner.event.ChangeListener;
import net.doepner.event.ChangePropagator;
import net.doepner.event.ChangeSupport;
import net.doepner.log.Log;
import net.doepner.log.LogProvider;

import static net.doepner.log.Log.Level.info;

public final class CanadianDeutsch implements LanguageChanger {

    private final Log log;

    private final ChangePropagator<Language> propagator = new ChangeSupport<>();

    private boolean deutsch;

    public CanadianDeutsch(LogProvider logProvider) {
        log = logProvider.getLog(getClass());
    }

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
        log.as(info, "Language changed from {} to {}", before, after);
    }

    @Override
    public void addListener(ChangeListener<Language> listener) {
        propagator.addListener(listener);
    }
}
