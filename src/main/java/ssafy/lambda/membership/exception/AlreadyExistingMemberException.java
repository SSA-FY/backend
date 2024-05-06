package ssafy.lambda.membership.exception;

/**
 * 이미 팀 내에 있는 멤버를 초대할 경우 발생하는 예외
 */
public class AlreadyExistingMemberException extends RuntimeException {

    public AlreadyExistingMemberException() {
        super("이미 존재하는 회원입니다.");
    }

}
