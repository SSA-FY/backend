package ssafy.lambda.vote.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ssafy.lambda.global.config.MinioConfig;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.member.exception.ImageUploadException;
import ssafy.lambda.member.service.MemberService;
import ssafy.lambda.membership.service.MembershipService;
import ssafy.lambda.notification.service.NotificationService;
import ssafy.lambda.team.entity.Team;
import ssafy.lambda.team.service.TeamService;
import ssafy.lambda.vote.dto.RequestReviewDto;
import ssafy.lambda.vote.dto.RequestVoteDto;
import ssafy.lambda.vote.dto.ResponseProfileWithPercentDto;
import ssafy.lambda.vote.dto.ResponseVoteDto;
import ssafy.lambda.vote.entity.Vote;
import ssafy.lambda.vote.entity.VoteInfo;
import ssafy.lambda.vote.repository.VoteInfoRepository;
import ssafy.lambda.vote.repository.VoteRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final MinioConfig minioConfig;
    private final MinioClient minioClient;

    private final VoteRepository voteRepository;
    private final VoteInfoRepository voteInfoRepository;
    private final MembershipService membershipService;
    private final TeamService teamService;
    private final MemberService memberService;
    private final NotificationService notificationService;

    @Override
    public void createVote(UUID memberId, Long teamId, RequestVoteDto requestVoteDto,
        MultipartFile img) {

        String url = minioConfig.getUrl() + "/vote/Default.jpeg";

        log.info("img = {}", img);
        if (img != null) {
            StringBuilder filename = new StringBuilder();
            filename.append(img.getOriginalFilename());
            filename.append("_");
            filename.append(UUID.randomUUID());
            filename.append(".");
            filename.append(StringUtils.getFilenameExtension(img.getOriginalFilename()));
            try {
                minioClient.putObject(
                    PutObjectArgs.builder()
                                 .bucket("vote")
                                 .object(filename.toString())
                                 .stream(
                                     img.getInputStream(), img.getSize(), -1)
                                 .contentType(img.getContentType())
                                 .build());
                url = minioConfig.getUrl() + "/vote/" + filename;
            } catch (Exception e) {
                throw new ImageUploadException();
            }
        }
        Vote vote = Vote.builder()
                        .content(requestVoteDto.getContent())
                        .imgUrl(url)
                        .membership(
                            membershipService.findMembershipByMemberIdAndTeamId(memberId, teamId))
                        .build();
        voteRepository.save(vote);
    }

    @Override
    public Long doVote(Long voteId, UUID voterId, UUID voteeId)
        throws IllegalArgumentException {

        Vote foundVote = validateVote(voteId);
        Member voter = memberService.findMemberById(voterId);
        Member votee = memberService.findMemberById(voteeId);

        // 이미 투표했는가
        if (voteInfoRepository.existsByVoteAndVoter(foundVote, voter)) {
            throw new IllegalArgumentException("The member already voted");
        }

        // 투표하기
        VoteInfo voteInfo = VoteInfo.builder()
                                    .voter(voter)
                                    .votee(votee)
                                    .vote(foundVote)
                                    .build();

        //투표한 정보 저장
        voteInfoRepository.save(voteInfo);
        //투표한 사람에게 마일리지 적립
        memberService.changePoint(voterId, "투표 완료 : " + foundVote.getContent(), 100L);
        //투표 받은 사람에게 알림 생성
        notificationService.createVoteNotification(votee, foundVote);
        return voteInfo.getId();
    }

    @Override
    public void review(UUID memberId, Long voteInfoId, RequestReviewDto requestReviewDto) {

        Vote foundVote = validateVote(requestReviewDto.getVoteId());
        Member member = memberService.findMemberById(memberId);

        // 투표했는가
        VoteInfo foundVoteInfo = voteInfoRepository.findByVoteAndVoter(foundVote, member)
                                                   .orElseThrow(
                                                       () -> new IllegalArgumentException(
                                                           "The member hasn't voted yet")
                                                   );

        // 이미 한줄평을 남겼는가
        if (foundVoteInfo.getOpinion() != null) {
            throw new IllegalArgumentException("The member already left a review");
        }

        foundVoteInfo.setOpinion(requestReviewDto.getReview());
        voteInfoRepository.save(foundVoteInfo);
        memberService.changePoint(memberId, "한줄 평 : " + foundVote.getContent(), 50L);
    }


    public List<ResponseProfileWithPercentDto> voteResult(Long voteId) {
        Vote foundVote = validateVote(voteId);

        List<ResponseProfileWithPercentDto> resultOfDto = voteRepository
            .findVoteInfoByCnt(voteId)
            .stream()
            .map(
                row -> {
                    UUID memberId = (UUID) row[0];
                    //                Long cnt = ((Number) ob[1]).longValue();
                    Double percent = ((Number) row[2]).doubleValue();

                    // TODO 쿼리 최대 6번 날라감, 최적화 필요
                    // MemberRepository에서 List<Member> findByIdIn(List<Long> memberIds); 메서드 선언 후
                    // List<Long> topVoteeIds를 파라미터로 넘겨줘서 컬렉션 조회 1번으로 줄이기
                    Member choosedMember = memberService.findMemberById(memberId);

                    return new ResponseProfileWithPercentDto(
                        choosedMember.getName(),
                        choosedMember.getProfileImgUrl(),
                        percent);
                }
            )
            .toList();

        return resultOfDto;

    }

    /* validateVote
     * 투표가 유효한지 검사하는 함수
     * 투표가 존재하는지, 진행중인지 확인한다
     * */
    private Vote validateVote(Long voteId) {
        Vote foundVote = voteRepository.findById(voteId)
                                       .orElseThrow(
                                           () -> new IllegalArgumentException("vote doesn't exist")
                                       );
        if (foundVote.getExpiredAt().isBefore(Instant.now())) {
            throw new IllegalArgumentException("vote is over");
        }
        return foundVote;
    }


    @Override
    public List<ResponseVoteDto> getVoteListByMember(UUID memberId, Long teamId) {
        Member member = memberService.findMemberById(memberId);
        Team team = teamService.findTeamById(teamId);
        return voteRepository.findVoteByVoterAndTeam(member, team);
    }

    @Override
    public List<Team> sortedTeamByVoteWhether(UUID memberId, List<Team> teamList) {
        Member member = memberService.findMemberById(memberId);
        return voteRepository.findByMemberAndVoteWhether(member, teamList);
    }
}
