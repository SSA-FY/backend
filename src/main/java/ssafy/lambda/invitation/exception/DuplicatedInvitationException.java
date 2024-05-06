package ssafy.lambda.invitation.exception;

public class DuplicatedInvitationException extends RuntimeException {

    public DuplicatedInvitationException() {
        super("이미 초대한 사용자 입니다.");
    }

}
