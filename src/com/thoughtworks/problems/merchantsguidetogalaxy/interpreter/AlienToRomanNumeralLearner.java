package com.thoughtworks.problems.merchantsguidetogalaxy.interpreter;

import com.thoughtworks.problems.merchantsguidetogalaxy.information.AlienToRomanNumeralMemory;

/**
 * Created by esgeronimo on 9/19/2015.
 */
public class AlienToRomanNumeralLearner extends Interpreter<Void> {

    private static final String REGEX = "\\b[a-zA-Z]+\\sis\\s(I|V|X|L|C|D|M)\\b";
    private static final String DELIMITER = "\\s";
    private static final int INDEX_ALIEN_NUMERAL_TOKEN = 0;
    private static final int INDEX_ROMAN_NUMERAL_TOKEN = 2;

    private AlienToRomanNumeralMemory alienToRomanNumeralMemory;

    public AlienToRomanNumeralLearner(AlienToRomanNumeralMemory alienToRomanNumeralMemory) {
        super(REGEX);
        this.alienToRomanNumeralMemory = alienToRomanNumeralMemory;
    }

    @Override
    public Void read(String statement) throws CannotInterpretException {
        if (!statement.matches(REGEX))
            throw new CannotInterpretException("What's with \"" + statement + "\"??? ");

        String[] tokens = statement.split(DELIMITER);
        alienToRomanNumeralMemory.gain(tokens[INDEX_ALIEN_NUMERAL_TOKEN], tokens[INDEX_ROMAN_NUMERAL_TOKEN].charAt(0));
        return null;
    }
}
