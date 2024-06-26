package ssafy.lambda.vote.exception;


public class VoteNotFoundException extends RuntimeException{

    public VoteNotFoundException() {
        super("투표 정보가 존재하지 않습니다.");
    }
}
