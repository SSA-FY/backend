package ssafy.lambda.member.exception;

import java.util.UUID;
import ssafy.lambda.member.entity.SocialType;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException() {
        super("회원을 찾을 수 없습니다.");
    }

    public MemberNotFoundException(UUID memberId) {
        super("회원을 찾을 수 없습니다. 회원번호 = " + memberId);
    }

    public MemberNotFoundException(String email, SocialType social) {
        super("회원을 찾을 수 없습니다. 이메일 = " + email + " / " + social);
    }

    public MemberNotFoundException(String tag) {
        super("회원을 찾을 수 없습니다. tag = " + tag);
    }
}
