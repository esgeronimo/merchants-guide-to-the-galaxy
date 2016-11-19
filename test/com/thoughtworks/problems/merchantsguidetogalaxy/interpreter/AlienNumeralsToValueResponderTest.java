package com.thoughtworks.problems.merchantsguidetogalaxy.interpreter;

import com.thoughtworks.problems.merchantsguidetogalaxy.information.AlienToRomanNumeralMemory;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by esgeronimo on 9/20/2015.
 */
public class AlienNumeralsToValueResponderTest {

    @Test
    public void testRead() throws Exception {
        AlienToRomanNumeralMemory alienToRomanNumeralMemory = Mockito.mock(AlienToRomanNumeralMemory.class);
        Mockito.when(alienToRomanNumeralMemory.recall("pish")).thenReturn('X');
        Mockito.when(alienToRomanNumeralMemory.recall("tegj")).thenReturn('L');
        Mockito.when(alienToRomanNumeralMemory.recall("glob")).thenReturn('I');

        RomanNumeralStockKnowledge romanNumeralStockKnowledge = Mockito.mock(RomanNumeralStockKnowledge.class);
        Mockito.when(romanNumeralStockKnowledge.read("XLII")).thenReturn(42);

        AlienNumeralsToValueResponder alienNumeralsToValueResponder = new AlienNumeralsToValueResponder(
                alienToRomanNumeralMemory, romanNumeralStockKnowledge);

        Assert.assertEquals("pish tegj glob glob is 42", alienNumeralsToValueResponder.read("how much is pish tegj glob glob ?"));

    }

    @Test(expected = Interpreter.CannotInterpretException.class)
    public void testReadWillThrowCannotInterpretExceptionIfRegexDoesNotMatch() throws Exception {
        AlienToRomanNumeralMemory alienToRomanNumeralMemory = Mockito.mock(AlienToRomanNumeralMemory.class);
        RomanNumeralStockKnowledge romanNumeralStockKnowledge = Mockito.mock(RomanNumeralStockKnowledge.class);
        AlienNumeralsToValueResponder alienNumeralsToValueResponder = new AlienNumeralsToValueResponder(
                alienToRomanNumeralMemory, romanNumeralStockKnowledge);
        alienNumeralsToValueResponder.read("this is definitely not a correct format");
    }

    @Test(expected = Interpreter.CannotInterpretException.class)
    public void testReadWillThrowCannotInterpretExceptionOnNoAlienToRomanNumeralMapping() throws Exception {
        AlienToRomanNumeralMemory alienToRomanNumeralMemory = Mockito.mock(AlienToRomanNumeralMemory.class);
        Mockito.when(alienToRomanNumeralMemory.recall(Mockito.anyString())).thenReturn(null);
        RomanNumeralStockKnowledge romanNumeralStockKnowledge = Mockito.mock(RomanNumeralStockKnowledge.class);
        AlienNumeralsToValueResponder alienNumeralsToValueResponder = new AlienNumeralsToValueResponder(
                alienToRomanNumeralMemory, romanNumeralStockKnowledge);
        alienNumeralsToValueResponder.read("pish tegj glob glob is 42");
    }

    @Test(expected = Interpreter.CannotInterpretException.class)
    public void testReadWillThrowCannotInterpretExceptionOnInvalidRomanNumeralFormatForDerivedValue() throws Exception {
        AlienToRomanNumeralMemory alienToRomanNumeralMemory = Mockito.mock(AlienToRomanNumeralMemory.class);
        Mockito.when(alienToRomanNumeralMemory.recall(Mockito.anyString())).thenReturn('X');
        RomanNumeralStockKnowledge romanNumeralStockKnowledge = Mockito.mock(RomanNumeralStockKnowledge.class);
        Mockito.when(romanNumeralStockKnowledge.read(Mockito.anyString())).thenThrow(
                new Interpreter.CannotInterpretException("Derived roman numeral value has wrong format"));
        AlienNumeralsToValueResponder alienNumeralsToValueResponder = new AlienNumeralsToValueResponder(
                alienToRomanNumeralMemory, romanNumeralStockKnowledge);
        alienNumeralsToValueResponder.read("pish tegj glob glob is 42");
    }
}
