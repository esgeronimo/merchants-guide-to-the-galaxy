package com.eugenegeronimo.merchantsguidetogalaxy.interpreter;

/**
 * Created by esgeronimo on 9/19/2015.
 */
public abstract class Interpreter<Return> {

    private final String REGEX;

    public Interpreter(String regex) {
        this.REGEX = regex;
    }

    public String getRegex() {
        return REGEX;
    }

    public abstract Return read(String statement) throws CannotInterpretException;

    public static class CannotInterpretException extends Exception {
        public CannotInterpretException(String message) {
            super(message);
        }
    }
}
