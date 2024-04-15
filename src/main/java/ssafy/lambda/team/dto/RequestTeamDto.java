package ssafy.lambda.team.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ssafy.lambda.team.entity.Team;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RequestTeamDto {

    private Long teamId;
    private String teamName;
    private String description;

    public Team toEntity() {
        return Team.builder()
            .teamId(teamId)
            .teamName(teamName)
            .description(description)
            .build();
    }
}
