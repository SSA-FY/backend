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

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MinioConfig minioConfig;
    private final MinioClient minioClient;

    private final MemberRepository memberRepository;

    @Override
    public Member createMember(RequestMemberUpdateDto requestMemberUpdateDto) {
        Member member = requestMemberUpdateDto.toEntity();

        return memberRepository.save(member);
    }

    @Override
    public Member findMemberById(UUID memberId) {
        return memberRepository.findById(memberId)
                               .orElseThrow(() -> new MemberNotFoundException(memberId));

    }

    @Override
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

    @Override
    public Boolean existsMemberByTag(String tag) {
        return memberRepository.existsByTag(tag);
    }

    @Override
    public void deleteMemberById(UUID memberId) {
        memberRepository.deleteById(memberId);
    }

    @Override
    @Transactional
    public List<Member> findAllMember() {
        return memberRepository.findAll();
    }

    @Override
    public Member findMemberByEmailAndSocial(String email, SocialType social) {
        return memberRepository.findByEmailAndSocial(email, social)
                               .orElseThrow(() -> new MemberNotFoundException(email, social));
    }

    @Override
    public List<Member> findAllMemberByTeamId(Long teamId) {
        return memberRepository.findAllByTeamId(teamId);
    }

    @Override
    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                               .orElseThrow(() -> new MemberNotFoundException(email));
    }
}
