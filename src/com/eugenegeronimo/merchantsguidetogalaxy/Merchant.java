package com.eugenegeronimo.merchantsguidetogalaxy;

import com.eugenegeronimo.merchantsguidetogalaxy.information.CreditRateMemory;
import com.eugenegeronimo.merchantsguidetogalaxy.interpreter.*;
import com.eugenegeronimo.merchantsguidetogalaxy.information.AlienToRomanNumeralMemory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by esgeronimo on 9/20/2015.
 */
public class Merchant {
    private static final Map<String, Interpreter> interpreters;
    static {
        interpreters = new HashMap<String, Interpreter>();

        AlienToRomanNumeralMemory alienToRomanNumeralMemory = new AlienToRomanNumeralMemory();
        CreditRateMemory creditRateMemory = new CreditRateMemory();
        RomanNumeralStockKnowledge romanNumeralStockKnowledge = new RomanNumeralStockKnowledge();

        AlienNumeralsToValueResponder alienNumeralsToValueResponder =
                new AlienNumeralsToValueResponder(alienToRomanNumeralMemory, romanNumeralStockKnowledge);
        interpreters.put(alienNumeralsToValueResponder.getRegex(), alienNumeralsToValueResponder);

        AlienToRomanNumeralLearner alienToRomanNumeralLearner =
                new AlienToRomanNumeralLearner(alienToRomanNumeralMemory);
        interpreters.put(alienToRomanNumeralLearner.getRegex(), alienToRomanNumeralLearner);

        CreditComputationResponder creditComputationResponder =
                new CreditComputationResponder(alienToRomanNumeralMemory, creditRateMemory, romanNumeralStockKnowledge);
        interpreters.put(creditComputationResponder.getRegex(), creditComputationResponder);

        CreditRateLearner creditRateLearner =
                new CreditRateLearner(creditRateMemory, alienToRomanNumeralMemory, romanNumeralStockKnowledge);
        interpreters.put(creditRateLearner.getRegex(), creditRateLearner);
    }

    public List<String> interact(List<String> messages) {
        List<String> responses = new ArrayList<String>();
        for (String message: messages) {
            try {
                Interpreter interpreter = selectInterpreter(message);
                Object response = interpreter.read(message);
                if (response instanceof String)
                    responses.add(response.toString());
            } catch (IllegalArgumentException e) {
                responses.add(e.getMessage());
            } catch (Interpreter.CannotInterpretException e) {
                responses.add(e.getMessage());
            }
        }
        return responses;
    }

    private Interpreter selectInterpreter(String message) throws IllegalArgumentException {
        for (String key : interpreters.keySet()) {
            if (message.matches(key))
                return interpreters.get(key);
        }
        throw new IllegalArgumentException("I have no idea what you are talking about");
    }

    public static void main(String args[]) {
        List<String> messages = new ArrayList<String>();
        messages.add("glob is I");
        messages.add("prok is V");
        messages.add("pish is X");
        messages.add("tegj is L");
        messages.add("glob glob Silver is 34 Credits");
        messages.add("glob prok Gold is 57800 Credits");
        messages.add("pish pish Iron is 3910 Credits");
        messages.add("how much is pish tegj glob glob ?");
        messages.add("how many Credits is glob prok Silver ?");
        messages.add("how many Credits is glob prok Gold ?");
        messages.add("how many Credits is glob prok Iron ?");
        messages.add("how much wood could a woodchuck chuck if a woodchuck could chuck wood ?");
        new Merchant().interact(messages);
    }
}
