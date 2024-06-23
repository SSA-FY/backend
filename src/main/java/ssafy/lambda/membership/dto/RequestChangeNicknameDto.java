package ssafy.lambda.membership.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestChangeNicknameDto {

    @NotBlank
    String nickname;
    String teamName;

}
