package com.thoughtworks.problems.merchantsguidetogalaxy.interpreter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by esgeronimo on 9/19/2015.
 */
public class RomanNumeralStockKnowledgeTest {

    private RomanNumeralStockKnowledge romanNumeralStockKnowledge;

    @Before
    public void setup() throws Exception {
        romanNumeralStockKnowledge = new RomanNumeralStockKnowledge();
    }

    @Test(expected = Interpreter.CannotInterpretException.class)
    public void testReadWillThrowCannotInterpretException() throws Exception {
        romanNumeralStockKnowledge.read("CMCDXCXLIXIV");
    }

    @Test
    public void testReadForThreeTimesSuccession() throws Exception {
        Assert.assertTrue(3000 == romanNumeralStockKnowledge.read("MMM"));
        Assert.assertTrue(300 == romanNumeralStockKnowledge.read("CCC"));
        Assert.assertTrue(30 == romanNumeralStockKnowledge.read("XXX"));
        Assert.assertTrue(3 == romanNumeralStockKnowledge.read("III"));
    }

    @Test
    public void testRead() throws Exception {
        Assert.assertTrue(999 == romanNumeralStockKnowledge.read("CMXCIX"));
        Assert.assertTrue(444 == romanNumeralStockKnowledge.read("CDXLIV"));
        Assert.assertTrue(3333 == romanNumeralStockKnowledge.read("MMMCCCXXXIII"));
        Assert.assertTrue(3999 == romanNumeralStockKnowledge.read("MMMCMXCIX"));
    }
}
