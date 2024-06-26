package ssafy.lambda.team.dto;

import lombok.Data;

@Data
public class RequestTeamUpdateDto {

    Long teamId;
    String teamName;
    String description;
    String managerTag;

}
