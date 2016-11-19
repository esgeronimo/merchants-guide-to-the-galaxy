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
public class CreditRateLearnerTest {

    @Test
    public void testRead() throws Exception {
        CreditRateMemory creditRateMemory = Mockito.mock(CreditRateMemory.class);
        Mockito.doNothing().when(creditRateMemory).gain(Mockito.anyString(), Mockito.any(CreditRate.class));

        AlienToRomanNumeralMemory alienToRomanNumeralMemory = Mockito.mock(AlienToRomanNumeralMemory.class);
        Mockito.when(alienToRomanNumeralMemory.recall("glob")).thenReturn('X');
        Mockito.when(alienToRomanNumeralMemory.recall("prok")).thenReturn('V');
        Mockito.when(alienToRomanNumeralMemory.recall("pish")).thenReturn('C');

        RomanNumeralStockKnowledge romanNumeralStockKnowledge = Mockito.mock(RomanNumeralStockKnowledge.class);
        Mockito.when(romanNumeralStockKnowledge.read("XX")).thenReturn(20);
        Mockito.when(romanNumeralStockKnowledge.read("XV")).thenReturn(25);
        Mockito.when(romanNumeralStockKnowledge.read("CC")).thenReturn(200);

        CreditRateLearner interpreter = new CreditRateLearner(creditRateMemory,
                alienToRomanNumeralMemory, romanNumeralStockKnowledge);

        CreditRate creditRate = interpreter.read("glob glob Silver is 34 Credits");
        Assert.assertEquals("Silver", creditRate.getProduct());
        Assert.assertTrue(20 == creditRate.getAmount());
        Assert.assertEquals(34.0f, creditRate.getCredit(), 0f);

        Mockito.doReturn(25).when(romanNumeralStockKnowledge).read("glob prok");
        creditRate = interpreter.read("glob prok Gold is 57800 Credits");
        Assert.assertEquals("Gold", creditRate.getProduct());
        Assert.assertTrue(25 == creditRate.getAmount());
        Assert.assertEquals(57800.0f, creditRate.getCredit(), 0f);

        Mockito.doReturn(200).when(romanNumeralStockKnowledge).read("pish pish");
        creditRate = interpreter.read("pish pish Iron is 3910 Credits");
        Assert.assertEquals("Iron", creditRate.getProduct());
        Assert.assertTrue(200 == creditRate.getAmount());
        Assert.assertEquals(3910.0f, creditRate.getCredit(), 0f);
    }

    @Test(expected = Interpreter.CannotInterpretException.class)
    public void testReadWillThrowCannotInterpretExceptionOnNoAlienToRomanNumeralMapping() throws Exception {
        CreditRateMemory creditRateMemory = Mockito.mock(CreditRateMemory.class);
        AlienToRomanNumeralMemory alienToRomanNumeralMemory = Mockito.mock(AlienToRomanNumeralMemory.class);
        Mockito.when(alienToRomanNumeralMemory.recall(Mockito.anyString())).thenReturn(null);
        RomanNumeralStockKnowledge romanNumeralStockKnowledge = Mockito.mock(RomanNumeralStockKnowledge.class);

        CreditRateLearner interpreter = new CreditRateLearner(creditRateMemory,
                alienToRomanNumeralMemory, romanNumeralStockKnowledge);
        interpreter.read("glob glob Silver is 34 Credits");
    }

    @Test(expected = Interpreter.CannotInterpretException.class)
    public void testReadWillThrowCannotInterpretExceptionOnFailureToInterpretDerivedRomanNumeral() throws Exception {
        CreditRateMemory creditRateMemory = Mockito.mock(CreditRateMemory.class);
        AlienToRomanNumeralMemory alienToRomanNumeralMemory = Mockito.mock(AlienToRomanNumeralMemory.class);
        Mockito.when(alienToRomanNumeralMemory.recall(Mockito.anyString())).thenReturn('X');
        RomanNumeralStockKnowledge romanNumeralStockKnowledge = Mockito.mock(RomanNumeralStockKnowledge.class);
        Mockito.when(romanNumeralStockKnowledge.read(Mockito.anyString())).thenThrow(
                new CreditRateLearner.CannotInterpretException("Error message"));

        CreditRateLearner interpreter = new CreditRateLearner(creditRateMemory,
                alienToRomanNumeralMemory, romanNumeralStockKnowledge);
        interpreter.read("glob glob Silver is 34 Credits");
    }
}
