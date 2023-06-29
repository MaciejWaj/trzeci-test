package pl.kurs.trzecitest.exception;

import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({DuplicateShapeException.class, CommandInvalidValueException.class})
    public ResponseEntity<ErrorMessage> badRequestException(RuntimeException runtimeException) {
        ErrorMessage message = new ErrorMessage(
                runtimeException.getMessage());

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ShapeNotFoundException.class, UserNotFoundException.class})
    public ResponseEntity<ErrorMessage> notFoundException(RuntimeException runtimeException) {
        ErrorMessage message = new ErrorMessage(
                runtimeException.getMessage());

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
