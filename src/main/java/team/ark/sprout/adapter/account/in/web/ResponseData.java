package team.ark.sprout.adapter.account.in.web;

import lombok.Value;
import org.springframework.http.HttpStatus;

@Value(staticConstructor = "of")
public class ResponseData<T> {
    int code;
    String message;
    T data;

    public static <T> ResponseData<T> create(HttpStatus status, T data) {
        return new ResponseData<>(status.value(), status.getReasonPhrase(), data);
    }
}
