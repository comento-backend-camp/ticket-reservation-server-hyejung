package comento.backend.ticket.config.customException;

import comento.backend.ticket.config.CustomException;
import comento.backend.ticket.config.ErrorCode;

public class DuplicatedException extends CustomException {
    public DuplicatedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
