package net.doepner.lang;

public enum LanguageEnum implements Language {

    CANADIAN("en-ca"),
    DEUTSCH("de");

    private final String code;

    private LanguageEnum(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    public String toString() {
        return capitalize(name());
    }

    private static String capitalize(String s) {
        return Character.toUpperCase(s.charAt(0))
            + s.toLowerCase().substring(1);
    }
}
