package ssafy.lambda.team.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestTeamDescriptionUpdateDto {

    @NotNull(message = "팀 ID 는 필수 입력 값 입니다.")
    Long teamId;
    String description;
}
