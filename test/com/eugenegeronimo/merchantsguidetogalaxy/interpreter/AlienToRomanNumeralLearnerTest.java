package com.eugenegeronimo.merchantsguidetogalaxy.interpreter;

import com.eugenegeronimo.merchantsguidetogalaxy.information.AlienToRomanNumeralMemory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by esgeronimo on 9/19/2015.
 */
public class AlienToRomanNumeralLearnerTest {

    private AlienToRomanNumeralMemory memory;

    @Before
    public void setup() throws Exception {
        memory = Mockito.mock(AlienToRomanNumeralMemory.class);
        Mockito.doNothing().when(memory).gain(Mockito.anyString(), Mockito.anyChar());
    }

    @Test(expected = Interpreter.CannotInterpretException.class)
    public void testReadWillThrowCannotInterpretExceptionOnDifferentFormat() throws Exception {
        AlienToRomanNumeralLearner interpreter = new AlienToRomanNumeralLearner(memory);
        interpreter.read("glob glob Silver is 34 Credits");
    }

    @Test(expected = Interpreter.CannotInterpretException.class)
    public void testReadWillThrowCannotInterpretExceptionOnNonRomanNumeralInput() throws Exception {
        AlienToRomanNumeralLearner interpreter = new AlienToRomanNumeralLearner(memory);
        interpreter.read("prok is K");
    }

    @Test
    public void testRead() throws Exception {
        AlienToRomanNumeralLearner interpreter = new AlienToRomanNumeralLearner(memory);
        interpreter.read("glob is I");
        interpreter.read("prok is V");
        interpreter.read("pish is X");
        interpreter.read("tegj is L");
        Assert.assertTrue(true);
    }
}
