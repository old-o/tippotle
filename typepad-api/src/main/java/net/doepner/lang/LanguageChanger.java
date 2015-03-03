package net.doepner.lang;


import org.guppy4j.event.ChangeNotifier;

public interface LanguageChanger extends LanguageProvider, ChangeNotifier<Language> {

    void changeLanguage();

}
