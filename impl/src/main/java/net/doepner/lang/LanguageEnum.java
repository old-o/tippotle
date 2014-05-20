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

    public static Language fromCode(String code) {
        for (Language language : values()) {
            if (language.getCode().equals(code)) {
                return language;
            }
        }
        return null;
    }
}
