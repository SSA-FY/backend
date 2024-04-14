package ssafy.lambda.membership.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RequestMembershipDto {

    private Long id;
    private Long memberId;
    private Long teamId;
}