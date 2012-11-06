package net.doepner.lang;


import net.doepner.ChangeListener;
import net.doepner.ChangeSupport;

public class EnglishOrGerman implements LanguageChanger {

    private final ChangeSupport changeSupport = new ChangeSupport();

	private boolean english;

	@Override
	public Language getLanguage() {
		return english ? Language.ENGLISH : Language.DEUTSCH;
	}

	@Override
	public void changeLanguage() {
		english = !english;
        changeSupport.handleChange();
	}

    @Override
    public void addListener(ChangeListener listener) {
        changeSupport.addListener(listener);
    }
}
