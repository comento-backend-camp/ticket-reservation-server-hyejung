package comento.backend.ticket.config;

import com.fasterxml.jackson.core.JsonParseException;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;

import java.util.*;

@RestControllerAdvice //@Conroller 전역에서 발생 가능한 예외를 잡아 처리
@Slf4j
public class GlobalExceptionHandler {
	private static ErrorCode errorCode;

	private GlobalExceptionHandler() {
	}

	//ConstraintViolationException.class 는 유효성 검사 실패시 (@Validated)
	@ExceptionHandler({JsonParseException.class,
		MethodArgumentNotValidException.class,
		ConstraintViolationException.class,
		MethodArgumentTypeMismatchException.class,
		MissingServletRequestParameterException.class})
	public static ResponseEntity<Object> missMatchExceptionHandler() {
		errorCode = ErrorCode.INVALID_VALUE;
		log.error(String.valueOf(errorCode.getHttpStatus()), errorCode.getMessage());
		return ResponseEntity.status(errorCode.getHttpStatus()).body(
			ErrorResponse.res(errorCode.getStatus(), errorCode.getMessage(), errorCode.getReason()));
	}

	@ExceptionHandler(CustomException.class)
	public static ResponseEntity<Object> customExceptionHandler(CustomException e) {
		errorCode = e.getErrorCode();
		log.error(String.valueOf(errorCode.getHttpStatus()), errorCode.getMessage());
		return ResponseEntity.status(errorCode.getHttpStatus()).body(
			ErrorResponse.res(errorCode.getStatus(), errorCode.getMessage(), errorCode.getReason()));
	}

	@ExceptionHandler({NoSuchElementException.class, NotFoundException.class})
	public static ResponseEntity<Object> notFoundExceptionHandler() {
		errorCode = ErrorCode.NOT_FOUND;
		log.error(String.valueOf(errorCode.getHttpStatus()), errorCode.getMessage());
		return ResponseEntity.status(errorCode.getHttpStatus()).body(
			ErrorResponse.res(errorCode.getStatus(), errorCode.getMessage(), errorCode.getReason()));
	}

	@ExceptionHandler(Exception.class)
	public static ResponseEntity<Object> illExceptionHandler(Exception e) {
		errorCode = ErrorCode.SERVER_ERROR;
		log.error(String.valueOf(errorCode.getHttpStatus()), errorCode.getMessage(), e);
		return ResponseEntity.status(errorCode.getHttpStatus()).body(
			ErrorResponse.res(errorCode.getStatus(), errorCode.getMessage(), errorCode.getReason()));
	}
}
