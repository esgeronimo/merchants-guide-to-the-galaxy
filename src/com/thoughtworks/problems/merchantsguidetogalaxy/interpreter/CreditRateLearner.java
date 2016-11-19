package com.thoughtworks.problems.merchantsguidetogalaxy.interpreter;

import com.thoughtworks.problems.merchantsguidetogalaxy.information.AlienToRomanNumeralMemory;
import com.thoughtworks.problems.merchantsguidetogalaxy.information.CreditRate;
import com.thoughtworks.problems.merchantsguidetogalaxy.information.CreditRateMemory;

/**
 * Created by esgeronimo on 9/19/2015.
 */
public class CreditRateLearner extends Interpreter<CreditRate> {

    private static final String REGEX = "[a-zA-Z ]+\\sis\\s[0-9]+\\sCredits";
    private static final String DELIMITER = "\\s";
    private static final int REVERSE_INDEX_PRODUCT_TOKEN = 4;
    private static final int REVERSE_INDEX_CREDIT_PER_AMOUNT_TOKEN = 2;

    private CreditRateMemory creditRateMemory;
    private AlienToRomanNumeralMemory alienToRomanNumeralMemory;
    private RomanNumeralStockKnowledge romanNumeralStockKnowledge;

    public CreditRateLearner(CreditRateMemory creditRateMemory,
                             AlienToRomanNumeralMemory alienToRomanNumeralMemory,
                             RomanNumeralStockKnowledge romanNumeralStockKnowledge) {
        super(REGEX);
        this.creditRateMemory = creditRateMemory;
        this.alienToRomanNumeralMemory = alienToRomanNumeralMemory;
        this.romanNumeralStockKnowledge = romanNumeralStockKnowledge;
    }

    @Override
    public CreditRate read(String statement) throws CannotInterpretException {
        if (!statement.matches(REGEX))
            throw new CannotInterpretException("The info says \"" + statement + "\". I think it has nothing to do with the rates.");

        String[] tokens = statement.split(DELIMITER);
        String amountInString = "";
        for (int index = 0; index < (tokens.length - REVERSE_INDEX_PRODUCT_TOKEN); index++) {
            Character romanNumeral = alienToRomanNumeralMemory.recall(tokens[index]);
            if (null == romanNumeral)
                throw new CannotInterpretException("I am not sure what \"" + tokens[index] + "\" is...");
            amountInString = amountInString.concat(romanNumeral.toString());
        }

        CreditRate creditRate = new CreditRate();
        creditRate.setProduct(tokens[tokens.length - REVERSE_INDEX_PRODUCT_TOKEN]);
        creditRate.setCredit(Float.valueOf(tokens[tokens.length - REVERSE_INDEX_CREDIT_PER_AMOUNT_TOKEN]));
        creditRate.setAmount(romanNumeralStockKnowledge.read(amountInString));
        creditRateMemory.gain(creditRate.getProduct(), creditRate);
        return creditRate;
    }
}
