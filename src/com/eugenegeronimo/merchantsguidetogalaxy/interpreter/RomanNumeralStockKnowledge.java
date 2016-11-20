package com.eugenegeronimo.merchantsguidetogalaxy.interpreter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by esgeronimo on 9/19/2015.
 */
public class RomanNumeralStockKnowledge extends Interpreter<Integer> {

    private static final String REGEX = "\\bM{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})\\b";
    private static final Map<Character, Integer> romanNumeralMapping;
    static {
        romanNumeralMapping = new HashMap<Character, Integer>();
        romanNumeralMapping.put('M', 1000);
        romanNumeralMapping.put('D', 500);
        romanNumeralMapping.put('C', 100);
        romanNumeralMapping.put('L', 50);
        romanNumeralMapping.put('X', 10);
        romanNumeralMapping.put('V', 5);
        romanNumeralMapping.put('I', 1);
    }

    public RomanNumeralStockKnowledge() {
        super(REGEX);
    }

    @Override
    public Integer read(String statement) throws CannotInterpretException{
        if (!statement.matches(REGEX)) {
            throw new CannotInterpretException("Invalid roman numeral: " + statement);
        }

        int sum = 0, previousValue = 1000, currentValue;
        char[] symbols = statement.toCharArray();
        for (int index = 0; index < symbols.length; index++) {
            sum += currentValue = romanNumeralMapping.get(symbols[index]);

            if (previousValue < currentValue)
                sum -= previousValue * 2;

            previousValue = currentValue;
        }

        return sum;
    }
}
