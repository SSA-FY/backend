package ssafy.lambda.point;

public class NotEnoughPointException extends RuntimeException {

    public NotEnoughPointException() {
        super("포인트가 충분하지 않습니다.");
    }
}
