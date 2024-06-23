package ssafy.lambda.board.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.lambda.board.dto.MemberWithPercenCntDto;
import ssafy.lambda.board.dto.ResponseBoardDetailDto;
import ssafy.lambda.board.dto.ResponseBoardSummaryDto;
import ssafy.lambda.board.dto.ResponseCommentDto;
import ssafy.lambda.board.entity.BoardComment;
import ssafy.lambda.board.entity.ExpiredVote;
import ssafy.lambda.board.repository.CommentRepository;
import ssafy.lambda.board.repository.ExpiredVoteRepository;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.member.service.MemberService;
import ssafy.lambda.membership.entity.Membership;
import ssafy.lambda.membership.service.MembershipService;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {


    private final CommentRepository commentRepository;
    private final ExpiredVoteRepository boardRepository;
    private final MemberService memberService;
    private final MembershipService membershipService;

    /////////////////////////////////////////////////////

    @Override
    public List<ResponseBoardSummaryDto> getBoardList(Long teamId, UUID memberId, Long page) {
        Membership membership = membershipService.findMembershipByMemberIdAndTeamId(memberId,
            teamId);
        Pageable pageable = PageRequest.of(page.intValue(), 10);
        List<ExpiredVote> boardList = boardRepository.findAllByTeamId(teamId, pageable);

        return boardList.stream()
                        .map(ev ->
                            {
                                ResponseBoardSummaryDto dto = ResponseBoardSummaryDto.builder()
                                                                                     .boardId(
                                                                                         ev.getId())
                                                                                     .content(
                                                                                         ev.getContent())
                                                                                     .createdAt(
                                                                                         ev.getCreatedAt())
                                                                                     .build();
                                boardRepository.findTopMemberByBoard(ev.getId())
                                               .stream()
                                               .forEach(
                                                   row -> {
                                                       dto.addMember((String) row[1]);
                                                   });
                                ;
                                return dto;
                            }
                        )
                        .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseBoardDetailDto getBoardDetail(Long expiredVoteId, Long page) {
        // 1. 유효성 검증
        ExpiredVote expiredVote = getExpriedVote(expiredVoteId);
        Long teamId = expiredVote.getMembership()
                                 .getTeam()
                                 .getTeamId();
        // 2. dto 생성
        Long totalCnt = boardRepository.getCntByBoard(expiredVoteId);
        ResponseBoardDetailDto dto = ResponseBoardDetailDto.builder()
                                                           .boardId(expiredVoteId)
                                                           .content(expiredVote.getContent())
                                                           .totalCnt(totalCnt)
                                                           .build();
        // 3. entity -> dto 변경
        boardRepository.findMemberCntByBoard(expiredVoteId, page)
                       .stream()
                       .forEach(row -> {

                           Membership membership = membershipService.findMembershipByMemberIdAndTeamId(
                               (UUID) row[0], teamId);
                           dto.addMember(MemberWithPercenCntDto.builder()
                                                               .memberTag(
                                                                   (String) row[1])
                                                               .memberName(
                                                                   membership.getNickname())
                                                               .profileImgUrl(
                                                                   (String) row[2])
                                                               .voteCnt(
                                                                   ((Number) row[3]).longValue())
                                                               .totalCnt(
                                                                   totalCnt)
                                                               .build());
                       });
        return dto;
    }

    /////////////////////////////////////////////////////

    @Override
    @Transactional(readOnly = true)
    public List<ResponseCommentDto> getCommentList(Long expiredVoteId, Long page) {
        // TODO 최적화
        // 1. 댓글 반환
        Pageable pageable = PageRequest.of(page.intValue(), 20, Sort.by(Sort.Direction.DESC, "id"));
        ExpiredVote expiredVote = getExpriedVote(expiredVoteId);
        Long teamId = expiredVote.getMembership()
                                 .getTeam()
                                 .getTeamId();
        List<BoardComment> commentList = commentRepository.findAllByExpiredVote(expiredVote,
            pageable);

        // 2. entity -> dto 변경 (member의 Tag와 그룹 내 NickName찾아서)
        return commentList.stream()
                          .map(bc -> {
                              Member commenter = bc.getCommenter();
                              Membership membership = membershipService.findMembershipByMemberIdAndTeamId(
                                  commenter.getMemberId(), teamId);
                              return new ResponseCommentDto(bc.getId(), membership.getNickname(),
                                  commenter.getTag(),
                                  bc.getContent(), bc.getCreatedAt());
                          })
                          .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public void writeComment(Long expiredVoteId, UUID memberId, String content) {
        // 1. 멤버랑 만료된 투표 정보 찾고 유효성 검사
        Member member = memberService.findMemberById(memberId);
        ExpiredVote expiredVote = getExpriedVote(expiredVoteId);
//        membershipService.findMembershipByMemberIdAndTeamId(memberId, expiredVote.getMembership().getTeam().getTeamId())
        // 2. 댓글 생성
        BoardComment comment = BoardComment.builder()
                                           .content(content)
                                           .commenter(member)
                                           .expiredVote(expiredVote)
                                           .build();
        // 저장
        commentRepository.save(comment);
    }


    @Override
    @Transactional
    public void editComment(Long commentId, UUID memberId, String content) {
        BoardComment comment = getCommentWithCommenter(commentId, memberId);
        comment.setContent(content);
        commentRepository.save(comment);
    }


    @Override
    @Transactional
    public void deleteComment(Long commentId, UUID memberId) {
        commentRepository.delete(getCommentWithCommenter(commentId, memberId));
    }

    /////////////////////////////////////////////////////

    private ExpiredVote getExpriedVote(Long expiredVoteId) {
        return boardRepository.findById(expiredVoteId)
                              .orElseThrow(() -> new IllegalArgumentException(
                                  "Board doesn't exist")
                              );
    }

    private BoardComment getCommentWithCommenter(Long commentId, UUID memberId) {
        Member commenter = memberService.findMemberById(memberId);
        BoardComment comment = commentRepository.findById(commentId)
                                                .orElseThrow(() -> new IllegalArgumentException(
                                                    "Comment doesn't exist")
                                                );
        if (!comment.getCommenter()
                    .equals(commenter)) {
            throw new IllegalArgumentException("This member isn't a commenter of this comment ");
        }
        return comment;
    }
}
