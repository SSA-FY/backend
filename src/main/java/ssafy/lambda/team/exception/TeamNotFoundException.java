package ssafy.lambda.team.exception;

public class TeamNotFoundException extends RuntimeException {

    public TeamNotFoundException() {
        super("팀을 찾을 수 없습니다.");
    }

    public TeamNotFoundException(String teamName) {
        super("해당 이름을 가진 팀이 존재하지 않습니다. 팀명 : " + teamName);
    }

    public TeamNotFoundException(Long teamId) {
        super("해당 번호을 가진 팀이 존재하지 않습니다. 팀 ID : " + teamId);
    }

}
