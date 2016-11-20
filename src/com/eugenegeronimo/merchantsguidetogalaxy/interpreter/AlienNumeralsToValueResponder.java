package com.eugenegeronimo.merchantsguidetogalaxy.interpreter;

import com.eugenegeronimo.merchantsguidetogalaxy.information.AlienToRomanNumeralMemory;

/**
 * Created by esgeronimo on 9/20/2015.
 */
public class AlienNumeralsToValueResponder extends Interpreter<String> {

    public static final String REGEX = "how\\smuch\\sis\\s[a-zA-Z0-9 ]+\\?";

    private static final String DELIMITER = "\\s";
    private static final int START_INDEX_ALIEN_NUMERALS = 3;

    private AlienToRomanNumeralMemory alienToRomanNumeralMemory;
    private RomanNumeralStockKnowledge romanNumeralStockKnowledge;

    public AlienNumeralsToValueResponder(AlienToRomanNumeralMemory alienToRomanNumeralMemory,
                                         RomanNumeralStockKnowledge romanNumeralStockKnowledge) {
        super(REGEX);
        this.alienToRomanNumeralMemory = alienToRomanNumeralMemory;
        this.romanNumeralStockKnowledge = romanNumeralStockKnowledge;
    }

    @Override
    public String read(String statement) throws CannotInterpretException {
        if (!statement.matches(REGEX))
            throw new CannotInterpretException("I have no idea what you are talking about");

        String[] tokens = statement.split(DELIMITER);
        String amountInAlienNumeral = "";
        String amountInString = "";
        for (int index = START_INDEX_ALIEN_NUMERALS; index < (tokens.length - 1); index++) {
            amountInAlienNumeral = amountInAlienNumeral.concat(tokens[index] + " ");
            Character romanNumeral = alienToRomanNumeralMemory.recall(tokens[index]);
            if (null == romanNumeral)
                throw new CannotInterpretException("I am not sure what \"" + tokens[index] + "\" is...");
            amountInString = amountInString.concat(romanNumeral.toString());
        }
        return amountInAlienNumeral + "is " + String.valueOf(romanNumeralStockKnowledge.read(amountInString));
    }
}
