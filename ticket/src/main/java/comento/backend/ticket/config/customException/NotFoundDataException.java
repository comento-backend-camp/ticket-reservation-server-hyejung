package comento.backend.ticket.config.customException;

public class NotFoundDataException extends RuntimeException{
    public NotFoundDataException() {
    }

    public NotFoundDataException(String message) {
        super(message);
    }
}
