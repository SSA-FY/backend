package ssafy.lambda.global.response.dto;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class ResponseData<T> extends Response {

    private final T data;

    public ResponseData(HttpStatus status, String message, T data) {
        super(status, message);
        this.data = data;
    }

    public static <T> ResponseEntity res(HttpStatus status, String message,
        T data) {
        return ResponseEntity
            .status(status)
            .body(new ResponseData<>(status, message, data));
    }
}
