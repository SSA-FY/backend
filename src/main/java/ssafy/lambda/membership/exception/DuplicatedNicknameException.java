package ssafy.lambda.membership.exception;

public class DuplicatedNicknameException extends RuntimeException {

    public DuplicatedNicknameException(String nickname) {
        super(nickname + " 은 이미 존재하는 닉네임 입니다.");
    }
}
