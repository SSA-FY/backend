package ssafy.lambda.team.exception;

public class TeamUnauthorizedException extends RuntimeException {

    public TeamUnauthorizedException() {
        super("그룹 정보 수정은 그룹 관리자만 가능합니다.");
    }
}
