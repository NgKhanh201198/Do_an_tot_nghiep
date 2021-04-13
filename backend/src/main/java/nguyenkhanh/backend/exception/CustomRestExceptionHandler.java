package nguyenkhanh.backend.exception;

import java.util.Date;
import java.util.concurrent.TimeoutException;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import nguyenkhanh.backend.response.MessageResponse;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ Exception.class })
	public final ResponseEntity<?> exception(Exception exception) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new MessageResponse(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
						HttpStatus.INTERNAL_SERVER_ERROR.name(), "Server error, please try again!"));
	}

	@ExceptionHandler({ NotFoundException.class })
	public final ResponseEntity<?> handleResourceNotFoundException(NotFoundException exception, WebRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new MessageResponse(new Date(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(),
						exception.getMessage(), request.getDescription(false)));
	}

	@ExceptionHandler({ EntityNotFoundException.class })
	public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException exception, WebRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new MessageResponse(new Date(), HttpStatus.NOT_FOUND.value(), exception.getMessage(),
						"Did not find the ID you requested", request.getDescription(false)));
	}

	@ExceptionHandler({ BadRequestException.class })
	public ResponseEntity<?> handleBadRequestException(BadRequestException exception, WebRequest request) {
		MessageResponse message = new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.name(), exception.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}
	
	// Kiem tra du lieu co ton tai khong
	@ExceptionHandler({ UsernameNotFoundException.class })
	public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException exception, WebRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new MessageResponse(new Date(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(),
						exception.getMessage(), request.getDescription(false)));
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<?> handleMaxSizeException(MaxUploadSizeExceededException exc, WebRequest request) {
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
				.body(new MessageResponse(new Date(), HttpStatus.EXPECTATION_FAILED.value(), "Expectation Failed",
						"File too large!", request.getDescription(false)));
	}

	@ExceptionHandler({ TimeoutException.class })
	public ResponseEntity<?> handlTimeoutException(TimeoutException exception, WebRequest request) {
		MessageResponse message = new MessageResponse(new Date(), HttpStatus.REQUEST_TIMEOUT.value(), "Request timeout",
				exception.getMessage());
		return new ResponseEntity<>(message, HttpStatus.REQUEST_TIMEOUT);
	}

	// Validation data
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return ResponseEntity.badRequest()
				.body(new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(),
						exception.getBindingResult().getFieldError().getDefaultMessage(),
						request.getDescription(false)));
	}

	// Not fond API
//	@Override
//	@ExceptionHandler(NoHandlerFoundException.class)
//	public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
//			HttpStatus status, WebRequest request) {
//		return ResponseEntity.badRequest()
//				.body(new MessageResponse(new Date(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(),
//						"NOT FOUND API",
//						request.getDescription(false)));
//	}

}
