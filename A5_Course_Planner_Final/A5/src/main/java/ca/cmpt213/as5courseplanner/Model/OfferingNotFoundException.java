package ca.cmpt213.as5courseplanner.Model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Exception class
 * @author Banana
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class OfferingNotFoundException extends RuntimeException {

    public OfferingNotFoundException() {
    }

    public OfferingNotFoundException(String message) {
        super(message);
    }

    public OfferingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public OfferingNotFoundException(Throwable cause) {
        super(cause);
    }

    public OfferingNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
