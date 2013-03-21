package net.doepner.lang;


import net.doepner.event.ChangeListener;
import net.doepner.event.ChangePropagator;
import net.doepner.event.ChangeSupport;

public class EnglishOrGerman implements LanguageChanger {

    private final ChangePropagator<Language> propagator =
            new ChangeSupport<>();

	private boolean english;

	@Override
	public Language getLanguage() {
		return english ? Language.ENGLISH : Language.DEUTSCH;
	}

	@Override
	public void changeLanguage() {
        final Language before = getLanguage();
        english = !english;
        final Language after = getLanguage();
        propagator.handleChange(before, after);
	}

    @Override
    public void addListener(ChangeListener<Language> listener) {
        propagator.addListener(listener);
    }
}
