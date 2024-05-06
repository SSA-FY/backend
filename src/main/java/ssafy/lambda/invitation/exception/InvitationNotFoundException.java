package ssafy.lambda.invitation.exception;

public class InvitationNotFoundException extends RuntimeException {

    public InvitationNotFoundException() {
        super("존재하지 않는 초대입니다.");
    }


}
