package comento.backend.ticket.config;

import comento.backend.ticket.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice //@Conroller 전역에서 발생 가능한 예외를 잡아 처리
@Slf4j
public class GlobalExceptionHandler {
    private static ErrorCode errorCode;

    /**
     * 특정 Exception을 지정하여 별도로 처리
     */

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public static ErrorResponse missMatchExceptionHandler(MethodArgumentTypeMismatchException e){
        errorCode = ErrorCode.INVALID_VALUE;
        log.error(errorCode.getStatus() + errorCode.getMessage(), e);
        return ErrorResponse.res(errorCode.getStatus(), errorCode.getMessage(), errorCode.getReason(), errorCode.getHttpStatus());
    }

    @ExceptionHandler(IllegalStateException.class)
    public static ErrorResponse illegalExceptionHandler(IllegalStateException ie){
        errorCode = ErrorCode.SERVER_ERROR;
        log.error(errorCode.getStatus()+ " " + errorCode.getMessage(), ie);
        return ErrorResponse.res(errorCode.getStatus(), errorCode.getMessage(), errorCode.getReason(), errorCode.getHttpStatus());
    }

    @ExceptionHandler(RuntimeException.class)
    public static ErrorResponse exceptionHandler(RuntimeException e){
        switch(e.getMessage()){
            case "400" :
                errorCode = ErrorCode.INVALID_VALUE;
                log.error(errorCode.getStatus()+ " " + errorCode.getMessage(), e);
                return ErrorResponse.res(errorCode.getStatus(), errorCode.getMessage(), errorCode.getReason(), errorCode.getHttpStatus());
            case "401" :
                errorCode = ErrorCode.NO_USER;
                log.error(errorCode.getStatus() + " " + errorCode.getMessage(), e);
                return ErrorResponse.res(errorCode.getStatus(), errorCode.getMessage(), errorCode.getReason(), errorCode.getHttpStatus());
            case "404" :
                errorCode = ErrorCode.NO_DATA;
                log.error(errorCode.getStatus()+ " " + errorCode.getMessage(), e);
                return ErrorResponse.res(errorCode.getStatus(), errorCode.getMessage(), errorCode.getReason(), errorCode.getHttpStatus());
            case "409" :
                if (e.getClass().getSimpleName().equals("UserService")) { //호출된 곳이 UserService
                    errorCode = ErrorCode.VALID_USER;
                    log.error(errorCode.getStatus()+ " " + errorCode.getMessage(), e);
                    return ErrorResponse.res(errorCode.getStatus(), errorCode.getMessage(), errorCode.getReason(), errorCode.getHttpStatus());
                }else{ //좌석 예약 시
                    errorCode = ErrorCode.VALID_SEAT;
                    log.error(errorCode.getStatus()+ " " + errorCode.getMessage(), e);
                    return ErrorResponse.res(errorCode.getStatus(), errorCode.getMessage(), errorCode.getReason(), errorCode.getHttpStatus());
                }
            default :
                errorCode = ErrorCode.SERVER_ERROR;
                log.error(errorCode.getStatus() + errorCode.getMessage(), e);
                return ErrorResponse.res(errorCode.getStatus(), errorCode.getMessage(), errorCode.getReason(), errorCode.getHttpStatus());
        }
    }
}
