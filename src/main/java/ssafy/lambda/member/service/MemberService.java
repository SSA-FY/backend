package ssafy.lambda.member.service;

import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;
import ssafy.lambda.member.dto.RequestMemberUpdateDto;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.member.entity.SocialType;

public interface MemberService {

    Member createMember(RequestMemberUpdateDto requestMemberUpdateDto);

    Member findMemberById(UUID memberId);

    Member updateMember(UUID memberId, RequestMemberUpdateDto requestMemberUpdateDto,
        MultipartFile img);

    Boolean existsMemberByTag(String tag);

    void deleteMemberById(UUID memberId);

    List<Member> findAllMember();

    Member findMemberByEmailAndSocial(String email, SocialType social);

    List<Member> findAllMemberByTeamId(Long teamId);

    Member findMemberByEmail(String email);

    List<Member> findMemberByTagLike(String tag);
}
