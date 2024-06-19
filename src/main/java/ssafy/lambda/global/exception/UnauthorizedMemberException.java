package ssafy.lambda.global.exception;

/**
 * 권한이 없는 유저가 (본인의 데이터가 아닌 데이터에 접근하려고 할 때)가
 * 요청을 했을 때 발생하는 예외
 */
public class UnauthorizedMemberException extends RuntimeException{
    public UnauthorizedMemberException() {
        super("권한이 없습니다.");
    }
}
