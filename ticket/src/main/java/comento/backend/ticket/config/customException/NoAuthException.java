package comento.backend.ticket.config.customException;

import comento.backend.ticket.config.CustomException;
import comento.backend.ticket.config.ErrorCode;

public class NoAuthException extends CustomException {
	public NoAuthException(ErrorCode errorCode) {
		super(errorCode);
	}
}
