package com.ark.inflearnback.configuration.handler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import com.ark.inflearnback.configuration.exception.DuplicateEmailException;
import com.ark.inflearnback.configuration.exception.RoleNotFoundException;
import com.ark.inflearnback.configuration.http.model.form.HttpResponse;
import java.util.Iterator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalRestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        return ResponseEntity.badRequest().body(HttpResponse.of(BAD_REQUEST, getResultMessage(ex)));
    }

    protected String getResultMessage(final MethodArgumentNotValidException ex) {
        final Iterator<ObjectError> iterator = ex.getBindingResult().getAllErrors().iterator();
        final StringBuilder resultMessageBuilder = new StringBuilder();
        while (iterator.hasNext()) {
            final ObjectError objectError = iterator.next();
            resultMessageBuilder.append(objectError.getDefaultMessage());
            if (iterator.hasNext()) {
                resultMessageBuilder.append(", ");
            }
        }
        return resultMessageBuilder.toString();
    }

    @ExceptionHandler
    public ResponseEntity<HttpResponse<String>> errorHandler(final DuplicateEmailException e) {
        return ResponseEntity.badRequest()
            .body(HttpResponse.of(HttpStatus.CONFLICT, e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<HttpResponse<String>> errorHandler(final RoleNotFoundException e) {
        return ResponseEntity.internalServerError()
            .body(HttpResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()));
    }

}
