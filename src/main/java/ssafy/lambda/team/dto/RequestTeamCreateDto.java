package ssafy.lambda.team.dto;

import lombok.Data;
import ssafy.lambda.team.entity.Team;

/**
 * 그룹 생성 요청 DTO
 */
@Data
public class RequestTeamCreateDto {

    String teamName;
    String description;
    String managerName;

    public Team toEntity() {
        return Team.builder()
                   .teamName(teamName)
                   .description(description)
                   .imgUrl("")
                   .build();
    }
}
