package ssafy.lambda.global.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ssafy.lambda.global.response.Response;
import ssafy.lambda.member.exception.MemberNotFoundException;
import ssafy.lambda.team.exception.TeamNotFoundException;
import ssafy.lambda.team.exception.TeamUnauthorizedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MemberNotFoundException.class)
    @ResponseBody
    public ResponseEntity<Response> handleMembersNotFoundException(MemberNotFoundException e) {
        return Response.res(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(TeamNotFoundException.class)
    @ResponseBody
    public ResponseEntity<Response> handleTeamNotFoundException(TeamNotFoundException e) {
        return Response.res(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(TeamUnauthorizedException.class)
    @ResponseBody
    public ResponseEntity<Response> handleTeamUnauthorizedException(TeamUnauthorizedException e) {
        return Response.res(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

}
