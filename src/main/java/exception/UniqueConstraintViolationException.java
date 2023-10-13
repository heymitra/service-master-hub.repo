package exception;

public class UniqueConstraintViolationException extends Exception {
    public UniqueConstraintViolationException(String message) {
        super(message);
    }
}