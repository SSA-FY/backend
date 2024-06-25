package ssafy.lambda.vote.exception;

public class VoteInfoNotFoundException extends RuntimeException {

    public VoteInfoNotFoundException() {
        super("투표 정보를 찾을 수 없습니다.");
    }
}
