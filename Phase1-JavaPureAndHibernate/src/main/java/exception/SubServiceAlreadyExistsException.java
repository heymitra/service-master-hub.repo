package exception;

public class SubServiceAlreadyExistsException extends RuntimeException {

    public SubServiceAlreadyExistsException(String subServiceName) {
        super("SubService with name '" + subServiceName + "' already exists.");
    }
}
