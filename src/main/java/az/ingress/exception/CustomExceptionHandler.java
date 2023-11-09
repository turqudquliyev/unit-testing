package az.ingress.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static az.ingress.model.constant.ExceptionConstant.UNEXPECTED_EXCEPTION;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ExceptionResponse handle(NotFoundException ex) {
        log.error("NotFoundException ex: ", ex);
        return ExceptionResponse.of(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ExceptionResponse handle(Exception ex) {
        log.error("Exception ex: ", ex);
        return ExceptionResponse.of(UNEXPECTED_EXCEPTION);
    }
}