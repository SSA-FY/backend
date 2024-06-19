package ssafy.lambda.member.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ssafy.lambda.global.config.MinioConfig;
import ssafy.lambda.member.dto.RequestMemberUpdateDto;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.member.entity.SocialType;
import ssafy.lambda.member.exception.ImageUploadException;
import ssafy.lambda.member.exception.MemberNotFoundException;
import ssafy.lambda.member.repository.MemberRepository;
import ssafy.lambda.point.service.PointService;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MinioConfig minioConfig;
    private final MinioClient minioClient;

    private final MemberRepository memberRepository;
    private final PointService pointService;

    public Member createMember(RequestMemberUpdateDto requestMemberUpdateDto) {
        Member member = requestMemberUpdateDto.toEntity();

        return memberRepository.save(member);
    }

    public Member findMemberById(UUID memberId) {
        return memberRepository.findById(memberId)
                               .orElseThrow(() -> new MemberNotFoundException(memberId));

    }

    @Transactional
    public Member updateMember(UUID memberId, RequestMemberUpdateDto requestMemberUpdateDto,
        MultipartFile img) {
        String url = minioConfig.getUrl() + "/member/NoProfile.png";

        if (img != null) {
            String filename =
                memberId + "." + StringUtils.getFilenameExtension(img.getOriginalFilename());

            try {
                minioClient.putObject(
                    PutObjectArgs.builder()
                                 .bucket("member")
                                 .object(filename)
                                 .stream(
                                     img.getInputStream(), img.getSize(), -1)
                                 .contentType(img.getContentType())
                                 .build());
                url = minioConfig.getUrl() + "/member/" + filename;
            } catch (Exception e) {
                throw new ImageUploadException();
            }
        }

        Member updatedMember = memberRepository.findById(memberId)
                                               .orElseThrow(
                                                   () -> new MemberNotFoundException(memberId));

        updatedMember.update(requestMemberUpdateDto.name(), requestMemberUpdateDto.tag(),
            url);

        return updatedMember;
    }

    public Boolean existsMemberByTag(String tag) {
        return memberRepository.existsByTag(tag);

    }

    public void deleteMemberById(UUID memberId) {
        memberRepository.deleteById(memberId);
    }

    @Transactional
    public List<Member> findAllMember() {
        return memberRepository.findAll();
    }

    public Member findMemberByEmailAndSocial(String email, SocialType social) {
        return memberRepository.findByEmailAndSocial(email, social)
                               .orElseThrow(() -> new MemberNotFoundException(email, social));
    }

    public List<Member> findAllMemberByTeamId(Long teamId) {
        return memberRepository.findAllByTeamId(teamId);
    }

    @Transactional
    public Member changePoint(UUID memberId, String description, Long amount) {
        Member member = memberRepository.findById(memberId)
                                        .orElseThrow(
                                            () -> new MemberNotFoundException(memberId));
        member.setPoint(member.getPoint() + amount);
        pointService.create(member, description, amount);

        return member;
    }
}
