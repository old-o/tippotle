package net.doepner.lang;


import org.guppy4j.event.ChangeListener;
import org.guppy4j.event.ChangePropagator;
import org.guppy4j.log.Log;
import org.guppy4j.log.LogProvider;

import static org.guppy4j.log.Log.Level.info;

public final class CanadianDeutsch implements LanguageChanger {

    private final Log log;

    private final ChangePropagator<Language> propagator;

    private boolean deutsch;

    public CanadianDeutsch(LogProvider logProvider,
                           ChangePropagator<Language> propagator) {
        this.propagator = propagator;
        log = logProvider.getLog(getClass());
        deutsch = false;
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
