package comento.backend.ticket.config.customException;

public class DuplicatedException extends RuntimeException{
    public DuplicatedException() {
    }

    public DuplicatedException(String message) {
        super(message);
    }
}
