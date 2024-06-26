package ssafy.lambda.team.dto;

import lombok.Getter;
import ssafy.lambda.team.entity.Team;

@Getter
public class ResponseTeamDto {

    private final Long teamId;
    private final String teamName;
    private final String description;
    private final String imgUrl;

    private String managerName;
    private String managerTag;

    public ResponseTeamDto(Team team) {
        this.teamId = team.getTeamId();
        this.teamName = team.getTeamName();
        this.description = team.getDescription();
        this.imgUrl = team.getImgUrl();
    }

    public void setManger(String managerName, String managerTag) {
        this.managerName = managerName;
        this.managerTag = managerTag;
    }
}
