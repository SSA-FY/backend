package ssafy.lambda.member.exception;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException() {
        super("회원을 찾을 수 없습니다.");
    }

    public MemberNotFoundException(Long id) {
        super("회원을 찾을 수 없습니다. 회원번호 = " + id);
    }

    public MemberNotFoundException(String email) {
        super("회원을 찾을 수 없습니다. 이메일 = " + email);
    }

}
