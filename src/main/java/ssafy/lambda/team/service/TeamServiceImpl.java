package ssafy.lambda.team.service;

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
import ssafy.lambda.global.exception.UnauthorizedMemberException;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.member.exception.ImageUploadException;
import ssafy.lambda.membership.dto.RequestChangeNicknameDto;
import ssafy.lambda.membership.entity.Membership;
import ssafy.lambda.membership.service.MembershipService;
import ssafy.lambda.team.dto.RequestTeamCreateDto;
import ssafy.lambda.team.dto.RequestTeamUpdateDto;
import ssafy.lambda.team.entity.Team;
import ssafy.lambda.team.exception.DuplicatedTeamNameException;
import ssafy.lambda.team.exception.ExitTeamException;
import ssafy.lambda.team.exception.TeamNotFoundException;
import ssafy.lambda.team.repository.TeamRepository;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final MinioConfig minioConfig;
    private final MinioClient minioClient;

    private final TeamRepository teamRepository;
    private final MembershipService membershipService;

    @Transactional
    public void createTeam(Member manager, RequestTeamCreateDto teamCreateDto, MultipartFile img) {
        if (teamRepository.findByTeamName(teamCreateDto.getTeamName())
                          .isPresent()) {
            throw new DuplicatedTeamNameException(teamCreateDto.getTeamName());
        }

        Team team = teamCreateDto.toEntity();
        team.setManager(manager);
        team.setImgUrl(uploadImg(team.getTeamId(), img));
        teamRepository.save(team);
        membershipService.createMembership(manager, team, teamCreateDto.getManagerName());
        
    }

    public Team findTeamById(Long teamId) {
        return teamRepository.findById(teamId)
                             .orElseThrow(
                                 () -> new TeamNotFoundException(teamId));
    }

    public void deleteTeam(Long teamId) {
        // TODO 관리자 검증 과정 추가
        teamRepository.deleteById(teamId);
    }

    public List<Team> findAllTeam() {
        return teamRepository.findAll();
    }

    @Override
    public Team findTeamByName(String teamName) {
        return teamRepository.findByTeamName(teamName)
                             .orElseThrow(
                                 () -> new TeamNotFoundException(teamName)
                             );
    }

    @Override
    public List<Team> findTeamByNameLike(String teamName) {
        List<Team> teams = teamRepository.findByTeamNameLike(teamName);
        return teams;
    }

    @Transactional
    @Override
    public void updateTeam(Member member, Member newManager, RequestTeamUpdateDto requestDto,
        MultipartFile img) {
        // 팀 찾기
        Long teamId = requestDto.getTeamId();
        Team team = teamRepository.findById(teamId)
                                  .orElseThrow(
                                      () -> new TeamNotFoundException(teamId)
                                  );

        // 팀의 관리자만 해당 요청을 보낼 수 있음
        if (!team.getManager()
                 .equals(member)) {
            throw new UnauthorizedMemberException();
        }

        // 팀명 중복검색
        String teamName = requestDto.getTeamName();
        if (teamRepository.findByTeamName(teamName)
                          .isPresent()) {
            throw new DuplicatedTeamNameException(teamName);
        }

        // 새로운 관리자 확인
        membershipService.findMembershipByMemberIdAndTeamId(
            newManager.getMemberId(), teamId)
        ;

        // 팀 정보 갱신
        team.setTeamName(teamName);
        team.setDescription(requestDto.getDescription());
        team.setManager(newManager);
        team.setImgUrl(uploadImg(teamId, img));
        teamRepository.save(team);
    }

    @Override
    public List<Team> findAllTeamByMemberId(UUID memberId) {
        return teamRepository.findAllByMemberId(memberId);
    }

    @Override
    @Transactional
    public void changeNickname(RequestChangeNicknameDto requestChangeNicknameDto, UUID memberId) {
        Team team = findTeamByName(requestChangeNicknameDto.getTeamName());
        Membership membership = membershipService.findMembershipByMemberIdAndTeamId(
            memberId, team.getTeamId());
        membership.setNickname(requestChangeNicknameDto.getNickname());
    }

    @Transactional
    public void changeManger(Member newManager, Team team) {
        membershipService.findMembershipByMemberIdAndTeamId(newManager.getMemberId(),
            team.getTeamId());
        team.setManager(newManager);
    }

    @Override
    @Transactional
    public void exitTeam(String teamName, Member member) {
        Team team = findTeamByName(teamName);
        // 관리자는 다른 팀원이 있을경우 나갈 수 없음
        if (team.getManager()
                .equals(member) && team.getMemberships()
                                       .size() != 1) {
            throw new ExitTeamException();
        }
        Membership membership = membershipService.findMembershipByMemberIdAndTeamId(
            member.getMemberId(), team.getTeamId());
        membershipService.deleteMembership(membership.getMembershipId());
        System.out.println(team.getMemberships()
                               .size() + " participate ");
        if (team.getMemberships()
                .size() == 1) {
            deleteTeam(team.getTeamId());
        }
    }

    /////////////////////////////////////////////////////

    private String uploadImg(Long teamId, MultipartFile img) {

        String url = minioConfig.getUrl() + "/team/default.jpg";

        if (img != null) {

            String filename =
                "team" + teamId + "." + StringUtils.getFilenameExtension(
                    img.getOriginalFilename());

            try {
                minioClient.putObject(
                    PutObjectArgs.builder()
                                 .bucket("team")
                                 .object(filename)
                                 .stream(
                                     img.getInputStream(), img.getSize(), -1)
                                 .contentType(img.getContentType())
                                 .build());
                url = minioConfig.getUrl() + "/team/" + filename;
            } catch (Exception e) {
                throw new ImageUploadException();
            }
        }

        return url;


    }
}
