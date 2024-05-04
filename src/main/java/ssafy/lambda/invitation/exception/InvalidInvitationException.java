package ssafy.lambda.invitation.exception;

/**
 * 초대 유효기간이 지났을 경우 발생하는 예외
 */
public class InvalidInvitationException extends RuntimeException {

    public InvalidInvitationException() {
        super("유효하지 않은 초대입니다.");
    }

}
