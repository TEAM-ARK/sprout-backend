package com.ark.inflearnback.config.handler;

import com.ark.inflearnback.config.model.HttpResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Iterator;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.badRequest().body(HttpResponse.of(String.valueOf(BAD_REQUEST.value()), getResultMessage(ex)));
    }

    protected String getResultMessage(final MethodArgumentNotValidException ex) {
        final Iterator<ObjectError> iterator = ex.getBindingResult().getAllErrors().iterator();
        final StringBuilder resultMessageBuilder = new StringBuilder();
        while (iterator.hasNext()) {
            final ObjectError objectError = iterator.next();
            resultMessageBuilder
                    .append("[")
                    .append(getPropertyName(objectError.getCodes()[0]))
                    .append("] ")
                    .append(objectError.getDefaultMessage());

            if (iterator.hasNext()) {
                resultMessageBuilder.append(", ");
            }
        }
        return resultMessageBuilder.toString();
    }

    private String getPropertyName(final String propertyPath) {
        return propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
    }
}
