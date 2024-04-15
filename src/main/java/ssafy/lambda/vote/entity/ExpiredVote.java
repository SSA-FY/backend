//package ssafy.lambda.vote.entity;
//
//import jakarta.persistence.*;
//import lombok.Builder;
//import lombok.Getter;
//import org.springframework.data.annotation.CreatedDate;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Getter
//public class ExpiredVote {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "expried_vote_id")
//    private Long id;
//
//    @Column(name = "content")
//    private String content;
//
//    @Column(name = "create_at")
//    private LocalDateTime createAt;
//
//    @Column(name = "expired_at")
//    private LocalDateTime expiredAt;
//
//    @Column(name = "img_url")
//    private String imgUrl;
//
//    @OneToMany(mappedBy = "expiredVote")
//    List<VoteInfo> voteInfoList = new ArrayList<>();
//
//    protected ExpiredVote() {
//    }
//
//
//    @Builder
//
//}
