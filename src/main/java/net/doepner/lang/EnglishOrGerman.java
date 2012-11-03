package net.doepner.lang;


public class EnglishOrGerman implements LanguageChanger {

	private boolean english;

	@Override
	public Language getLanguage() {
		return english ? Language.ENGLISH : Language.DEUTSCH;
	}

	@Override
	public void changeLanguage() {
		english = !english;	
	}
}
