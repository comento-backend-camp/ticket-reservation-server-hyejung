package comento.backend.ticket.config.customException;

import comento.backend.ticket.config.CustomException;
import comento.backend.ticket.config.ErrorCode;

public class NotFoundDataException extends CustomException {
    public NotFoundDataException(ErrorCode errorCode) {
        super(errorCode);
    }
}
