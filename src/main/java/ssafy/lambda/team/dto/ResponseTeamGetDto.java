package ssafy.lambda.team.dto;

import java.time.Instant;
import lombok.Getter;
import ssafy.lambda.team.entity.Team;

@Getter
public class ResponseTeamGetDto {

    private final Long teamId;
    private final String teamName;
    private final String description;
    private final Instant createdAt;

    public ResponseTeamGetDto(Team team) {
        this.teamId = team.getTeamId();
        this.teamName = team.getTeamName();
        this.description = team.getDescription();
        this.createdAt = team.getCreatedAt();
    }
}
