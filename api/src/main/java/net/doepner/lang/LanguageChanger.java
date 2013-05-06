package net.doepner.lang;


import net.doepner.event.ChangeNotifier;

public interface LanguageChanger extends LanguageProvider,
    ChangeNotifier<ILanguage> {

    void changeLanguage();

}
