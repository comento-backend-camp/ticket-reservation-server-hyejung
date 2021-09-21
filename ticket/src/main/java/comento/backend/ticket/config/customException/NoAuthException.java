package comento.backend.ticket.config.customException;

public class NoAuthException extends RuntimeException{
    public NoAuthException() {
    }

    public NoAuthException(String message) {
        super(message);
    }
}
