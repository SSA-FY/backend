package ssafy.lambda.board.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ssafy.lambda.board.dto.ResponseCommentDto;
import ssafy.lambda.board.entity.ExpiredVote;
import ssafy.lambda.board.entity.VoteComment;
import ssafy.lambda.board.repository.CommentRepository;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.member.service.MemberService;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {


    private final CommentRepository commentRepository;
    /////////////////////////////////////////////////////
    private final MemberService memberService;


    @Override
    public List<ResponseCommentDto> getComments(Long expriedVoteId) {
//        List<VoteComment> commentList = commentRepository.findByExpiredVoteId(expriedVoteId);

        return commentRepository.findAllByVoteId(expriedVoteId)
                                .stream()
                                .map(
                                    comment ->
                                        new ResponseCommentDto(((Number) comment[0]).longValue(),
                                            (String) comment[1],
                                            (String) comment[2], (LocalDateTime) comment[3])
                                )
                                .toList();

    }

    @Override
    public void writeComment(Long voteId, Long memberId, String content) {
        Member member = memberService.findMemberById(memberId);
        // TODO expiredVote 객체 넣기
        ExpiredVote expiredVote = null;
        VoteComment comment = VoteComment.builder()
                                         .content(content)
                                         .member(member)
                                         .expiredVote(expiredVote)
                                         .build();
        commentRepository.save(comment);
    }


    @Override
    public void editComment(Long commentId, Long memberId, String content) {
        // TODO expiredVote 객체 넣기
        ExpiredVote expiredVote = null;
        VoteComment comment = commentRepository.findById(commentId)
                                               .orElseThrow(() -> new IllegalArgumentException(
                                                   "Comment doesn't exist")
                                               );
        if (comment.getMember()
                   .getMemberId() != memberId) {
            throw new IllegalArgumentException();
        }
        comment.setContent(content);
        commentRepository.save(comment);
    }


    @Override
    public void deleteComment(Long commentId) {
        VoteComment comment = commentRepository.findById(commentId)
                                               .orElseThrow(() -> new IllegalArgumentException(
                                                   "Comment doesn't exist")
                                               );
        commentRepository.delete(comment);
    }
}
