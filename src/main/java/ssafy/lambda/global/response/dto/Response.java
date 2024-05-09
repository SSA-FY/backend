package ssafy.lambda.global.response.dto;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class Response {

    private final HttpStatus status;
    private final String message;

    public Response(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ResponseEntity res(HttpStatus status, String message) {
        return ResponseEntity
            .status(status)
            .body(new Response(status, message));
    }
}
