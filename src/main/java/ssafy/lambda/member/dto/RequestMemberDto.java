package ssafy.lambda.member.dto;

import java.util.UUID;
import lombok.Builder;
import ssafy.lambda.member.entity.Member;


@Builder
public record RequestMemberDto(
    UUID memberId,
    String name,
    String tag,
    String profileImgUrl
) {

    public Member toEntity() {
        return Member.builder()
                     .memberId(memberId)
                     .name(name)
                     .tag(tag)
                     .profileImgUrl(profileImgUrl)
                     .build();
    }
}
