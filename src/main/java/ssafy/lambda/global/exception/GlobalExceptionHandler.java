package ssafy.lambda.global.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ssafy.lambda.global.response.Response;
import ssafy.lambda.invitation.exception.DuplicatedInvitationException;
import ssafy.lambda.invitation.exception.InvalidInvitationException;
import ssafy.lambda.invitation.exception.InvitationNotFoundException;
import ssafy.lambda.member.exception.MemberNotFoundException;
import ssafy.lambda.membership.exception.AlreadyExistingMemberException;
import ssafy.lambda.membership.exception.DuplicatedNicknameException;
import ssafy.lambda.team.exception.TeamNotFoundException;
import ssafy.lambda.team.exception.TeamUnauthorizedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<Response> handleMembersNotFoundException(MemberNotFoundException e) {
        return Response.res(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(TeamNotFoundException.class)
    public ResponseEntity<Response> handleTeamNotFoundException(TeamNotFoundException e) {
        return Response.res(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(TeamUnauthorizedException.class)
    public ResponseEntity<Response> handleTeamUnauthorizedException(TeamUnauthorizedException e) {
        return Response.res(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(DuplicatedInvitationException.class)
    public ResponseEntity<Response> handleDuplicatedInvitationException(
        DuplicatedInvitationException e) {
        return Response.res(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(InvitationNotFoundException.class)
    public ResponseEntity<Response> handleInvitationNotFoundException(
        InvitationNotFoundException e
    ) {
        return Response.res(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(AlreadyExistingMemberException.class)
    public ResponseEntity<Response> handleAlreadyExistingMemberException(
        AlreadyExistingMemberException e
    ) {
        return Response.res(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(InvalidInvitationException.class)
    public ResponseEntity<Response> handleInvalidInvitationException(
        InvalidInvitationException e
    ) {
        return Response.res(HttpStatus.GONE, e.getMessage());
    }

    @ExceptionHandler(DuplicatedNicknameException.class)
    public ResponseEntity<Response> handleDuplicatedNicknameException(
        DuplicatedNicknameException e) {
        return Response.res(HttpStatus.CONFLICT, e.getMessage());
    }

}
