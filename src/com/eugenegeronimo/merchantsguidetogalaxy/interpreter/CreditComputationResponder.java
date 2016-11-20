package com.eugenegeronimo.merchantsguidetogalaxy.interpreter;

import com.eugenegeronimo.merchantsguidetogalaxy.information.CreditRate;
import com.eugenegeronimo.merchantsguidetogalaxy.information.CreditRateMemory;
import com.eugenegeronimo.merchantsguidetogalaxy.information.AlienToRomanNumeralMemory;

/**
 * Created by esgeronimo on 9/20/2015.
 */
public class CreditComputationResponder extends Interpreter<String> {

    private static final String REGEX = "how\\smany\\sCredits\\sis\\s[a-zA-Z0-9 ]+\\?";
    private static final String DELIMITER = "\\s";
    private static final int START_INDEX_ALIEN_NUMERALS = 4;
    private static final int REVERSE_INDEX_PRODUCT = 2;

    private AlienToRomanNumeralMemory alienToRomanNumeralMemory;
    private CreditRateMemory creditRateMemory;
    private RomanNumeralStockKnowledge romanNumeralStockKnowledge;

    public CreditComputationResponder(AlienToRomanNumeralMemory alienToRomanNumeralMemory,
                                      CreditRateMemory creditRateMemory,
                                      RomanNumeralStockKnowledge romanNumeralStockKnowledge) {
        super(REGEX);
        this.alienToRomanNumeralMemory = alienToRomanNumeralMemory;
        this.creditRateMemory = creditRateMemory;
        this.romanNumeralStockKnowledge = romanNumeralStockKnowledge;
    }

    @Override
    public String read(String statement) throws CannotInterpretException {
        if (!statement.matches(REGEX))
            throw new CannotInterpretException("I have no idea what you are talking about");

        String[] tokens = statement.split(DELIMITER);
        String amountInAlienNumeral = "";
        String amountInRomanNumeral = "";
        for (int index = START_INDEX_ALIEN_NUMERALS; index < (tokens.length - REVERSE_INDEX_PRODUCT); index++) {
            amountInAlienNumeral = amountInAlienNumeral.concat(tokens[index] + " ");
            Character romanNumeral = alienToRomanNumeralMemory.recall(tokens[index]);
            if (null == romanNumeral)
                throw new CannotInterpretException("I am not sure what \"" + tokens[index] + "\" is...");
            amountInRomanNumeral = amountInRomanNumeral.concat(romanNumeral.toString());
        }

        CreditRate creditRate = creditRateMemory.recall(tokens[tokens.length - REVERSE_INDEX_PRODUCT]);
        if (null == creditRate) {
            throw new CannotInterpretException("I do not know the value for " +
                    tokens[tokens.length - REVERSE_INDEX_PRODUCT]);
        }

        Float derivedCredit =
                romanNumeralStockKnowledge.read(amountInRomanNumeral) *
                        (creditRate.getCredit() / creditRate.getAmount());

        return amountInAlienNumeral + tokens[tokens.length - REVERSE_INDEX_PRODUCT] +
                " is " + derivedCredit.toString() + " Credits";
    }
}
