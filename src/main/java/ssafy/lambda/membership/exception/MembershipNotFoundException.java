package ssafy.lambda.membership.exception;

public class MembershipNotFoundException extends RuntimeException {

    public MembershipNotFoundException() {
        super("Membership 을 찾지 못했습니다.");
    }

}
