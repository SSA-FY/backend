package ssafy.lambda.accesstoken.exception;

public class RefreshTokenNotFoundException extends RuntimeException {

    public RefreshTokenNotFoundException() {
        super("Refresh Token을 찾을 수 없습니다.");
    }

}
