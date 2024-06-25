package ssafy.lambda.board.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import ssafy.lambda.global.common.BaseEntity;
import ssafy.lambda.member.entity.Member;

@Entity
@Getter
public class BoardComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_comment_id")
    private Long id;

    @Column
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expired_vote_id")
    private ExpiredVote expiredVote;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    // 회원탈퇴를 할때만 댓글삭제(멤버가 팀을 나가도 댓글 유지)
    @JoinColumn(name = "member_id")
    private Member commenter;

    protected BoardComment() {

    }

    @Builder
    public BoardComment(String content, ExpiredVote expiredVote, Member commenter) {
        this.content = content;
        this.expiredVote = expiredVote;
        this.commenter = commenter;
    }


    public void setContent(String content) {
        this.content = content;
    }
}
