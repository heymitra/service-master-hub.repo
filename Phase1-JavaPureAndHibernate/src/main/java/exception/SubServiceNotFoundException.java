package exception;

public class SubServiceNotFoundException extends RuntimeException {
    public SubServiceNotFoundException(Long id) {
        super("SubService or Expert with the provided ID " + id + " not found.");
    }
}
