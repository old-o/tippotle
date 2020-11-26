package org.oldo.lang;


import org.guppy4j.event.ChangeNotifier;

public interface LanguageChanger extends LanguageProvider, ChangeNotifier<Language> {

    void changeLanguage();

}
