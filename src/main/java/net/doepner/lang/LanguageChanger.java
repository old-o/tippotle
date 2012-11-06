package net.doepner.lang;


import net.doepner.ChangeNotifier;

public interface LanguageChanger extends LanguageContext, ChangeNotifier {

	void changeLanguage();

}
