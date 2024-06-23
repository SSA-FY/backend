package ssafy.lambda.team.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import ssafy.lambda.team.entity.Team;

/**
 * 그룹 생성 요청 DTO
 */
@Data
public class RequestTeamCreateDto {

    @NotBlank(message = "팀명은 비워둘 수 없습니다.")
    String teamName;
    String description;
    String imgUrl;

    public Team toEntity() {
        return Team.builder()
                   .teamName(teamName)
                   .description(description)
                   .imgUrl(imgUrl)
                   .build();
    }
}
