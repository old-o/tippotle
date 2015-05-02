package net.doepner.lang;

/**
 * Enumerates supported languages
 */
public enum LanguageEnum implements Language {

    CANADIAN("en-ca"),
    DEUTSCH("de");

    private final String code;

    LanguageEnum(String code) {
        this.code = code;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String toString() {
        return capitalize(name());
    }

    private static String capitalize(String s) {
        return Character.toUpperCase(s.charAt(0))
                + s.toLowerCase().substring(1);
    }
}
