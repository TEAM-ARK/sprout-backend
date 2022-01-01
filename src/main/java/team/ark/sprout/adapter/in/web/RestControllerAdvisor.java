package team.ark.sprout.adapter.in.web;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import java.util.Iterator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestControllerAdvisor extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.badRequest()
            .body(ResponseData.create(BAD_REQUEST, getResultMessage(ex)));
    }

    protected String getResultMessage(MethodArgumentNotValidException ex) {
        Iterator<ObjectError> iterator = ex.getBindingResult()
            .getAllErrors()
            .iterator();

        StringBuilder resultMessageBuilder = new StringBuilder();

        while (iterator.hasNext()) {
            ObjectError objectError = iterator.next();
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

    private String getPropertyName(String propertyPath) {
        return propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
    }
}
