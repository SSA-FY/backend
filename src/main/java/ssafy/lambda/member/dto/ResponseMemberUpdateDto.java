package ssafy.lambda.member.dto;

import lombok.Builder;

@Builder
public record ResponseMemberUpdateDto(
    String tag,
    String name,
    String profileImgUrl
) {

}
