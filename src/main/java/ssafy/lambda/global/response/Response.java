package ssafy.lambda.global.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@Getter
@Setter
@ToString
@Builder
public class Response {

    private HttpStatus status;
    private String message;

    public static ResponseEntity<Response> res(HttpStatus status, String message) {
        return ResponseEntity
            .status(status)
            .body(Response.builder()
                .message(message)
                .status(status)
                .build());
    }
}
