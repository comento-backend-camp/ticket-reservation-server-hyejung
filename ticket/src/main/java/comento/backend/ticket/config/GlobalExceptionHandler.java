package comento.backend.ticket.config;

import com.fasterxml.jackson.core.JsonParseException;
import comento.backend.ticket.config.customException.DuplicatedException;
import comento.backend.ticket.config.customException.NoAuthException;
import comento.backend.ticket.config.customException.NotFoundDataException;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.NoSuchElementException;

@RestControllerAdvice //@Conroller 전역에서 발생 가능한 예외를 잡아 처리
@Slf4j
public class GlobalExceptionHandler {
    private static ErrorCode errorCode;

    /**
     * 특정 Exception을 지정하여 별도로 처리
     */

    //ConstraintViolationException.class 는 유효성 검사 실패시 (@Validated)
    @ExceptionHandler({JsonParseException.class,
            MethodArgumentNotValidException.class,
                    ConstraintViolationException.class,
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class})
    public static ResponseEntity missMatchExceptionHandler(Throwable t){
        errorCode = ErrorCode.INVALID_VALUE;
        log.error(errorCode.getStatus() + " " + errorCode.getMessage(), t);
        return new ResponseEntity<>(ErrorResponse.res(errorCode.getStatus(), errorCode.getMessage(), errorCode.getReason()),
                errorCode.getHttpStatus());
    }

    @ExceptionHandler(NoAuthException.class)
    public static ResponseEntity noAuthExceptionHandler(NoAuthException e){
        errorCode = ErrorCode.NO_USER;
        log.error(errorCode.getStatus() + errorCode.getMessage(), e);
        return new ResponseEntity<>(ErrorResponse.res(errorCode.getStatus(), errorCode.getMessage(), errorCode.getReason()),
                errorCode.getHttpStatus());
    }

    @ExceptionHandler({NoSuchElementException.class, NotFoundException.class, NotFoundDataException.class})
    public static ResponseEntity notFoundExceptionHandler(Throwable t){
        errorCode = ErrorCode.NO_DATA;
        log.error(errorCode.getStatus() + " " + errorCode.getMessage(), t);
        return new ResponseEntity<>(ErrorResponse.res(errorCode.getStatus(), errorCode.getMessage(), errorCode.getReason()),
                errorCode.getHttpStatus());
    }

    @ExceptionHandler(DuplicatedException.class)
    public static ResponseEntity duplicateExceptionHandler(DuplicatedException e){
        if (e.getMessage() == "UserService") { //호출된 곳이 UserService
            errorCode = ErrorCode.INVALID_USER;
            log.error(errorCode.getStatus() + " " + errorCode.getMessage(), e);
        }else{ //좌석 예약 시
            errorCode = ErrorCode.INVALID_SEAT;
            log.error(errorCode.getStatus() + " " + errorCode.getMessage(), e);
        }
        return new ResponseEntity<>(ErrorResponse.res(errorCode.getStatus(), errorCode.getMessage(), errorCode.getReason()),
                errorCode.getHttpStatus());
    }

    @ExceptionHandler({IllegalStateException.class, RuntimeException.class})
    public static ResponseEntity IllExceptionHandler(Throwable t){
        errorCode = ErrorCode.SERVER_ERROR;
        log.error(errorCode.getStatus() + " " + errorCode.getMessage(), t);
        return new ResponseEntity<>(ErrorResponse.res(errorCode.getStatus(), errorCode.getMessage(), errorCode.getReason()),
                errorCode.getHttpStatus());
    }
}
