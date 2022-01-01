package team.ark.sprout.adapter.http;

import lombok.Value;
import org.springframework.http.HttpStatus;

@Value(staticConstructor = "of")
class ResponseData<T> {
    int code;
    String message;
    T data;

    static <T> ResponseData<T> create(HttpStatus status, T data) {
        return new ResponseData<>(status.value(), status.getReasonPhrase(), data);
    }
}
