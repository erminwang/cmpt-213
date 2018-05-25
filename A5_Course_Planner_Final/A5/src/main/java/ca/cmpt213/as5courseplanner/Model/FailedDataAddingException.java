package ca.cmpt213.as5courseplanner.Model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class
 * @author Banana
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FailedDataAddingException extends RuntimeException {

    public FailedDataAddingException() {
    }

    public FailedDataAddingException(String message) {
        super(message);
    }

    public FailedDataAddingException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedDataAddingException(Throwable cause) {
        super(cause);
    }

    public FailedDataAddingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
