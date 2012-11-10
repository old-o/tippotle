package net.doepner.lang;


import net.doepner.event.ChangeListener;
import net.doepner.event.ChangePropagator;
import net.doepner.event.ChangeSupport;

public class EnglishOrGerman implements LanguageChanger {

    private final ChangePropagator propagator = new ChangeSupport();

	private boolean english;

	@Override
	public Language getLanguage() {
		return english ? Language.ENGLISH : Language.DEUTSCH;
	}

	@Override
	public void changeLanguage() {
		english = !english;
        propagator.handleChange();
	}

    @Override
    public void addListener(ChangeListener listener) {
        propagator.addListener(listener);
    }
}
