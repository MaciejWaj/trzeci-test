package pl.kurs.trzecitest.exception;

public class CommandInvalidValueException extends RuntimeException {

    public CommandInvalidValueException(String message) {
        super(message);
    }
}
