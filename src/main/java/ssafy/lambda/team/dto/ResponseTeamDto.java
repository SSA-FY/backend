package ssafy.lambda.team.dto;

import java.time.Instant;
import lombok.Getter;
import ssafy.lambda.team.entity.Team;

@Getter
public class ResponseTeamDto {

    private final Long teamId;
    private final String teamName;
    private final String description;
    private final Instant createdAt;
    private final Integer participants;
    private final String managerName;
    private final String managerTag;
    private final String imgUrl;

    public ResponseTeamDto(Team team) {
        this.teamId = team.getTeamId();
        this.teamName = team.getTeamName();
        this.description = team.getDescription();
        this.createdAt = team.getCreatedAt();
        this.participants = team.getParticipants();
        this.managerName = team.getManager()
                               .getName();
        this.managerTag = team.getManager()
                              .getTag();
        this.imgUrl = team.getImgUrl();
    }
}
