package ssafy.lambda.board.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.lambda.board.dto.ResponseCommentDto;
import ssafy.lambda.board.entity.BoardComment;
import ssafy.lambda.board.entity.ExpiredVote;
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
    public List<ResponseCommentDto> getCommentList(Long expiredVoteId) {
//        List<BoardComment> commentList = commentRepository.findByExpiredVoteId(expriedVoteId);
        return commentRepository.findAllByVoteId(expiredVoteId);

    }

    @Override
    public void writeComment(Long expiredVoteId, Long memberId, String content) {
        Member member = memberService.findMemberById(memberId);
        // TODO expiredVote 객체 넣기
        ExpiredVote expiredVote = null;
        BoardComment comment = BoardComment.builder()
                                           .content(content)
                                           .commenter(member)
                                           .expiredVote(expiredVote)
                                           .build();
        commentRepository.save(comment);
    }


    @Override
    @Transactional
    public void editComment(Long commentId, Long memberId, String content) {
        // TODO expiredVote 객체 넣기
        ExpiredVote expiredVote = null;
        BoardComment comment = commentRepository.findById(commentId)
                                                .orElseThrow(() -> new IllegalArgumentException(
                                                    "Comment doesn't exist")
                                                );
        if (comment.getCommenter()
                   .getMemberId() != memberId) {
            throw new IllegalArgumentException("This member isn't a commenter of this comment ");
        }
        comment.setContent(content);
        commentRepository.save(comment);
    }


    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        BoardComment comment = commentRepository.findById(commentId)
                                                .orElseThrow(() -> new IllegalArgumentException(
                                                    "Comment doesn't exist")
                                                );
        commentRepository.delete(comment);
    }
}
