package comento.backend.ticket.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {

    OK(200, HttpStatus.OK, "OK"),
    CREATED(201, HttpStatus.CREATED, "CREATED");

    private final Integer status;
    private final HttpStatus httpStatus;
    private final String message;
}
