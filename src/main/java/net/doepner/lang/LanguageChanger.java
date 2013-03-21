package net.doepner.lang;


import net.doepner.event.ChangeNotifier;

public interface LanguageChanger extends LanguageContext,
        ChangeNotifier<Language> {

	void changeLanguage();

}
