package net.doepner.text;

public interface CharCondition {
	
	boolean matches(char c);

    CharCondition LETTER = new CharCondition() {
        @Override
        public boolean matches(char c) {
            return Character.isLetter(c);
        }
    };

    CharCondition DIGIT = new CharCondition() {
        @Override
        public boolean matches(char c) {
            return Character.isDigit(c);
        }
    };

    CharCondition IS_WORD_PART = new CharCondition() {
		@Override
		public boolean matches(char c) {
			return Character.isLetter(c) || "-'".indexOf(c) != -1;
		}
	};

	CharCondition IS_NUMBER_PART = new CharCondition() {
		@Override
		public boolean matches(char c) {
			return Character.isDigit(c) || ".,-+".indexOf(c) != -1;
		}
	};
}
