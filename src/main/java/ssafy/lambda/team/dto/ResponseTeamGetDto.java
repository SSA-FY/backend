package ssafy.lambda.team.dto;

import lombok.Getter;
import ssafy.lambda.team.entity.Team;

@Getter
public class ResponseTeamGetDto {

    private final String teamName;
    private final String imgUrl;

    public ResponseTeamGetDto(Team team) {
        this.teamName = team.getTeamName();
        this.imgUrl = team.getImgUrl();

    }
}
