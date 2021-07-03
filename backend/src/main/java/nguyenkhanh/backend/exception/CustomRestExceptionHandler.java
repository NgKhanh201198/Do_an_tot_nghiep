package nguyenkhanh.backend.exception;

import java.util.Date;
import java.util.concurrent.TimeoutException;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Value;
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
    @Value("${spring.servlet.multipart.max-file-size}")
    private String MAX_FILE_SIZE;

    @ExceptionHandler({Exception.class})
    public final ResponseEntity<?> handleAllException(Exception exception, WebRequest request) {
        MessageResponse message = new MessageResponse(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.name(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({NotFoundException.class})
    public final ResponseEntity<?> handleResourceNotFoundException(NotFoundException exception, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MessageResponse(new Date(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(),
                        exception.getMessage(), request.getDescription(false)));
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException exception, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MessageResponse(new Date(), HttpStatus.NOT_FOUND.value(), exception.getMessage(),
                        "Không tìm thấy ID bạn yêu cầu", request.getDescription(false)));
    }

    // Kiem tra du lieu co ton tai khong
    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException exception, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MessageResponse(new Date(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(),
                        exception.getMessage(), request.getDescription(false)));
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<?> handleBadRequestException(BadRequestException exception, WebRequest request) {
        MessageResponse message = new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(), exception.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({TimeoutException.class})
    public ResponseEntity<?> handlTimeoutException(TimeoutException exception) {
        return new ResponseEntity<>(new MessageResponse(new Date(), HttpStatus.REQUEST_TIMEOUT.value(),
                HttpStatus.REQUEST_TIMEOUT.name(), exception.getMessage()), HttpStatus.REQUEST_TIMEOUT);
    }

    // Validation data
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.badRequest().body(new MessageResponse(new Date(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(),
                exception.getBindingResult().getFieldError().getDefaultMessage(), request.getDescription(false)));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleMaxSizeException(MaxUploadSizeExceededException exc, WebRequest request) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(new MessageResponse(new Date(), HttpStatus.EXPECTATION_FAILED.value(), HttpStatus.EXPECTATION_FAILED.name(),
                        "Tệp tên quá lớn. Kích thước tối đa " + MAX_FILE_SIZE, request.getDescription(false)));
    }
}
