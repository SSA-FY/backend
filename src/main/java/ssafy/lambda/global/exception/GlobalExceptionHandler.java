package ssafy.lambda.global.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ssafy.lambda.global.response.dto.Response;
import ssafy.lambda.invitation.exception.DuplicatedInvitationException;
import ssafy.lambda.invitation.exception.InvalidInvitationException;
import ssafy.lambda.invitation.exception.InvitationNotFoundException;
import ssafy.lambda.member.exception.MemberNotFoundException;
import ssafy.lambda.membership.exception.AlreadyExistingMemberException;
import ssafy.lambda.membership.exception.DuplicatedNicknameException;
import ssafy.lambda.notification.exception.NotificationNotFoundException;
import ssafy.lambda.point.NotEnoughPointException;
import ssafy.lambda.team.exception.DuplicatedTeamNameException;
import ssafy.lambda.team.exception.ExitTeamException;
import ssafy.lambda.team.exception.TeamNotFoundException;
import ssafy.lambda.team.exception.TeamUnauthorizedException;
import ssafy.lambda.vote.exception.VoteInfoNotFoundException;
import ssafy.lambda.vote.exception.VoteNotFoundException;

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

    @ExceptionHandler(DuplicatedTeamNameException.class)
    public ResponseEntity<Response> handleDuplicatedTeamNameException(
        DuplicatedTeamNameException e
    ) {
        return Response.res(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(UnauthorizedMemberException.class)
    public ResponseEntity<Response> handleUnauthorizedMemberException(
        UnauthorizedMemberException e) {
        return Response.res(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(NotificationNotFoundException.class)
    public ResponseEntity<Response> handleNotificationNotFoundException(
        NotificationNotFoundException e) {
        return Response.res(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(ExitTeamException.class)
    public ResponseEntity<Response> handleExitTeamException(ExitTeamException e) {
        return Response.res(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(NotEnoughPointException.class)
    public ResponseEntity<Response> handleNotEnoughPointExceptionException(NotEnoughPointException e) {
        return Response.res(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(VoteNotFoundException.class)
    public ResponseEntity<Response> handleVoteNotFoundException(
        VoteNotFoundException e) {
        return Response.res(HttpStatus.NOT_FOUND, e.getMessage());
    }
    @ExceptionHandler(VoteInfoNotFoundException.class)
    public ResponseEntity<Response> handleVoteInfoNotFoundException(
        VoteInfoNotFoundException e) {
        return Response.res(HttpStatus.NOT_FOUND, e.getMessage());
    }
}
