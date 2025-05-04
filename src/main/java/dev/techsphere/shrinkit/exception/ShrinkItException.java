package dev.techsphere.shrinkit.exception;

public class ShrinkItException extends Exception {

    public ShrinkItException (final String msg) {
        super(msg);
    }

    public ShrinkItException(final Throwable cause) {
        super(cause);
    }

    public ShrinkItException(final String msg, final Throwable throwable){
        super(msg, throwable);
    }
}
