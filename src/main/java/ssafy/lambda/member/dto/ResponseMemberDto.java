package ssafy.lambda.member.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.member.entity.SocialType;
import ssafy.lambda.membership.entity.Membership;

@Getter
public class ResponseMemberDto {

    private final Long memberId;
    private final SocialType social;
    private final String name;
    private final Integer point;
    private final LocalDateTime createdAt;
    private final String profileImgUrl;
    private final String email;
    private final List<Membership> memberships;

    public ResponseMemberDto(Member member) {
        this.memberId = member.getMemberId();
        this.social = member.getSocial();
        this.name = member.getName();
        this.point = member.getPoint();
        this.createdAt = member.getCreatedAt();
        this.profileImgUrl = member.getProfileImgUrl();
        this.email = member.getEmail();
        this.memberships = member.getMemberships();
    }
}
