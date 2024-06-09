package ssafy.lambda.member.exception;

import java.util.UUID;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException() {
        super("회원을 찾을 수 없습니다.");
    }

    public MemberNotFoundException(UUID memberId) {
        super("회원을 찾을 수 없습니다. 회원번호 = " + memberId);
    }

    public MemberNotFoundException(String email) {
        super("회원을 찾을 수 없습니다. 이메일 = " + email);
    }

}
