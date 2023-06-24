package com.shail.shrinkit.exception;

public class ShrinkItException extends Exception {

    public ShrinkItException (final String msg) {
        super(msg);
    }

    public ShrinkItException(final Throwable cause) {
        super(cause);
    }
}
