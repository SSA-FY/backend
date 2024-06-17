package ssafy.lambda.team.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestTeamUpdateDto {

    @NotNull(message = "팀 ID 는 필수 입력 값 입니다.")
    Long teamId;
    @NotBlank(message = "팀명은 비워둘 수 없습니다.")
    String teamName;
    String description;
}
