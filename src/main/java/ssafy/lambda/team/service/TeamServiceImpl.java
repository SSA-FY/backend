package ssafy.lambda.team.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.membership.dto.RequestChangeNicknameDto;
import ssafy.lambda.membership.entity.Membership;
import ssafy.lambda.membership.service.MembershipService;
import ssafy.lambda.team.dto.RequestTeamCreateDto;
import ssafy.lambda.team.dto.RequestTeamUpdateDto;
import ssafy.lambda.team.entity.Team;
import ssafy.lambda.team.exception.DuplicatedTeamNameException;
import ssafy.lambda.team.exception.ExitTeamException;
import ssafy.lambda.team.exception.TeamNotFoundException;
import ssafy.lambda.team.exception.TeamUnauthorizedException;
import ssafy.lambda.team.repository.TeamRepository;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final MembershipService membershipService;

    @Transactional
    public void createTeam(RequestTeamCreateDto teamCreateDto, Member manager) {
        if (teamRepository.findByTeamName(teamCreateDto.getTeamName())
                          .isPresent()) {
            throw new DuplicatedTeamNameException(teamCreateDto.getTeamName());
        }
        Team team = teamCreateDto.toEntity();
        team.setManager(manager);
        teamRepository.save(team);
        membershipService.createMembership(manager, team, "관리자");
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
    public void updateTeam(RequestTeamUpdateDto requestTeamUpdateDto, Member member) {
        if (teamRepository.findByTeamName(requestTeamUpdateDto.getTeamName())
                          .isPresent()) {
            throw new DuplicatedTeamNameException(requestTeamUpdateDto.getTeamName());
        }
        Team team = findTeamById(requestTeamUpdateDto.getTeamId());
        if (team.getManager()
                .equals(member)) {
            throw new TeamUnauthorizedException();
        }
        team.setTeamName(requestTeamUpdateDto.getTeamName());
        team.setDescription(requestTeamUpdateDto.getDescription());
        team.setImgUrl(requestTeamUpdateDto.getImgUrl());
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
}
