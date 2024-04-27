package ssafy.lambda.global.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ssafy.lambda.global.response.Response;
import ssafy.lambda.member.exception.MemberNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MemberNotFoundException.class)
    @ResponseBody
    public ResponseEntity<Response> handleNotFoundException(MemberNotFoundException e) {
        return Response.res(HttpStatus.NOT_FOUND, e.getMessage());
    }

}
