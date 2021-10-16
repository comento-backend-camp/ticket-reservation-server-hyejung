package comento.backend.ticket.config.customException;

public class NoDataException extends RuntimeException{
    public NoDataException() {
    }

    public NoDataException(String message) {
        super(message);
    }
}
