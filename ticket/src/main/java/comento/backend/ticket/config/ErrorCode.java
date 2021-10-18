package comento.backend.ticket.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /**
     * @see : 예외케이스 정의
     */

    INVALID_VALUE(400, HttpStatus.BAD_REQUEST, "BAD REQUEST", "잘못된 요청입니다. 다시 요청해주세요."),
    NO_USER(401, HttpStatus.UNAUTHORIZED,"UNAUTHORIZED",  "등록되지 않은 사용자입니다"),
    NOT_FOUND(404, HttpStatus.NOT_FOUND, "NOT FOUND ERROR", "요청할 수 없는 리소스입니다."),
    INVALID_USER(409, HttpStatus.CONFLICT, "CONFLICT", "이미 존재하는 사용자입니다."),
    INVALID_SEAT(409, HttpStatus.CONFLICT, "CONFLICT", "이미 예약된 좌석입니다."),
    SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "INTERVAL_SERVER ERROR", "서버 에러입니다.");

    private final Integer status;
    private final HttpStatus httpStatus;
    private final String message;
    private final String reason;
}
