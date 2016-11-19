package com.thoughtworks.problems.merchantsguidetogalaxy.interpreter;

import com.thoughtworks.problems.merchantsguidetogalaxy.information.AlienToRomanNumeralMemory;
import com.thoughtworks.problems.merchantsguidetogalaxy.information.CreditRate;
import com.thoughtworks.problems.merchantsguidetogalaxy.information.CreditRateMemory;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by esgeronimo on 9/20/2015.
 */
public class CreditComputationResponderTest {

    @Test
    public void testRead() throws Exception {
        AlienToRomanNumeralMemory alienToRomanNumeralMemory = Mockito.mock(AlienToRomanNumeralMemory.class);
        Mockito.when(alienToRomanNumeralMemory.recall("glob")).thenReturn('I');
        Mockito.when(alienToRomanNumeralMemory.recall("prok")).thenReturn('V');

        CreditRateMemory creditRateMemory = Mockito.mock(CreditRateMemory.class);
        CreditRate creditRate = new CreditRate();
        creditRate.setCredit(57800.0f);
        creditRate.setProduct("Gold");
        creditRate.setAmount(4);
        Mockito.when(creditRateMemory.recall("Gold")).thenReturn(creditRate);

        RomanNumeralStockKnowledge romanNumeralStockKnowledge = Mockito.mock(RomanNumeralStockKnowledge.class);
        Mockito.when(romanNumeralStockKnowledge.read("IV")).thenReturn(4);

        CreditComputationResponder creditComputationResponder = new CreditComputationResponder(alienToRomanNumeralMemory,
                creditRateMemory, romanNumeralStockKnowledge);
        Assert.assertEquals("glob prok Gold is 57800.0 Credits",
                creditComputationResponder.read("how many Credits is glob prok Gold ?"));
    }

    @Test
    public void testReadWillThrowCannotInterpretExceptionIfRegexDoesNotMatch() throws Exception {
        AlienToRomanNumeralMemory alienToRomanNumeralMemory = Mockito.mock(AlienToRomanNumeralMemory.class);
        CreditRateMemory creditRateMemory = Mockito.mock(CreditRateMemory.class);
        RomanNumeralStockKnowledge romanNumeralStockKnowledge = Mockito.mock(RomanNumeralStockKnowledge.class);

        CreditComputationResponder creditComputationResponder = new CreditComputationResponder(alienToRomanNumeralMemory,
                creditRateMemory, romanNumeralStockKnowledge);
        try {
            creditComputationResponder.read("this is definitely not a correct format");
        } catch (Interpreter.CannotInterpretException e) {
            Assert.assertEquals("I have no idea what you are talking about", e.getMessage());
        }
    }

    @Test
    public void testReadWillThrowCannotInterpretExceptionIfAlienToRomanNumeralMemoryRecallIsNull() throws Exception {
        AlienToRomanNumeralMemory alienToRomanNumeralMemory = Mockito.mock(AlienToRomanNumeralMemory.class);
        Mockito.when(alienToRomanNumeralMemory.recall(Mockito.anyString())).thenReturn(null);
        CreditRateMemory creditRateMemory = Mockito.mock(CreditRateMemory.class);
        RomanNumeralStockKnowledge romanNumeralStockKnowledge = Mockito.mock(RomanNumeralStockKnowledge.class);

        CreditComputationResponder creditComputationResponder = new CreditComputationResponder(alienToRomanNumeralMemory,
                creditRateMemory, romanNumeralStockKnowledge);

        try {
            creditComputationResponder.read("how many Credits is glob prok Gold ?");
        } catch (Interpreter.CannotInterpretException e) {
            Assert.assertEquals("I am not sure what \"glob\" is...", e.getMessage());
        }
    }

    @Test
    public void testReadWillThrowCannotInterpretExceptionIfCreditRateMemoryRecallIsNull() throws Exception {
        AlienToRomanNumeralMemory alienToRomanNumeralMemory = Mockito.mock(AlienToRomanNumeralMemory.class);
        Mockito.when(alienToRomanNumeralMemory.recall(Mockito.anyString())).thenReturn('V');
        CreditRateMemory creditRateMemory = Mockito.mock(CreditRateMemory.class);
        Mockito.when(creditRateMemory.recall("Gold")).thenReturn(null);
        RomanNumeralStockKnowledge romanNumeralStockKnowledge = Mockito.mock(RomanNumeralStockKnowledge.class);

        CreditComputationResponder creditComputationResponder = new CreditComputationResponder(alienToRomanNumeralMemory,
                creditRateMemory, romanNumeralStockKnowledge);

        try {
            creditComputationResponder.read("how many Credits is glob prok Gold ?");
        } catch(Interpreter.CannotInterpretException e) {
            Assert.assertEquals("I do not know the value for Gold", e.getMessage());
        }
    }

    @Test
    public void testReadWillThrowCannotInterpretExceptionIfRomanNumeralStockKnowledgeReadIsInvalid() throws Exception {
        AlienToRomanNumeralMemory alienToRomanNumeralMemory = Mockito.mock(AlienToRomanNumeralMemory.class);
        Mockito.when(alienToRomanNumeralMemory.recall("glob")).thenReturn('I');
        Mockito.when(alienToRomanNumeralMemory.recall("prok")).thenReturn('V');
        CreditRateMemory creditRateMemory = Mockito.mock(CreditRateMemory.class);
        Mockito.when(creditRateMemory.recall("Gold")).thenReturn(new CreditRate());
        RomanNumeralStockKnowledge romanNumeralStockKnowledge = Mockito.mock(RomanNumeralStockKnowledge.class);
        Mockito.when(romanNumeralStockKnowledge.read("IV")).thenThrow(
                new Interpreter.CannotInterpretException("Error Message"));

        CreditComputationResponder creditComputationResponder = new CreditComputationResponder(alienToRomanNumeralMemory,
                creditRateMemory, romanNumeralStockKnowledge);

        try {
            creditComputationResponder.read("how many Credits is glob prok Gold ?");
        } catch(Interpreter.CannotInterpretException e) {
            Assert.assertEquals("Error Message", e.getMessage());
        }
    }
}
