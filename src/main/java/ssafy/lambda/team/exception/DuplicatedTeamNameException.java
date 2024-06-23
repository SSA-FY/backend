package ssafy.lambda.team.exception;

public class DuplicatedTeamNameException extends RuntimeException {

    public DuplicatedTeamNameException(String teamName) {
        super("중복된 팀 명 : " + teamName);
    }

}
