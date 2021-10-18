package comento.backend.ticket.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    OK(200, HttpStatus.OK, "OK"),
    CREATED(201, HttpStatus.CREATED, "CREATED"),
    NO_DATA(204, HttpStatus.NO_CONTENT, "데이터가 존재하지 않습니다");

    private final Integer status;
    private final HttpStatus httpStatus;
    private final String message;
}
