package ssafy.lambda.team.exception;

public class ExitTeamException extends RuntimeException {

    public ExitTeamException() {
        super("관리자는 팀을 나갈 수 없습니다.");
    }

}
