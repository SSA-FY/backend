package ssafy.lambda.vote.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class VoteItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_item_id")
    private Long id;

    @Column(name = "count")
    private Long count;


    @Column(name = "profile_url")
    private String profileUrl;

    @ManyToOne
    @JoinColumn(name = "vote_id")
    private Vote vote;

    protected VoteItem(){}

    @Builder
    public VoteItem(Long id, Long count, String profileUrl, Vote vote) {
        this.id = id;
        this.count = count;
        this.profileUrl = profileUrl;
        this.vote = vote;
    }
}
