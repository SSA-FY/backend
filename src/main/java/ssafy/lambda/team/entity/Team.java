package ssafy.lambda.team.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ssafy.lambda.global.common.BaseEntity;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.membership.entity.Membership;

@Entity
@Getter
public class Team extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;

    @Column
    @Setter
    private String teamName;

    @Column
    @Setter
    private String description;

    @Setter
    @Column
    private Integer participants;

    @Setter
    @Column
    private String imgUrl;

    @OneToMany(mappedBy = "team", orphanRemoval = true)
    private List<Membership> memberships = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    @JoinColumn(nullable = false, updatable = false, name = "manager_id")
    private Member manager;

    protected Team() {
    }

    @Builder
    public Team(Long teamId, String teamName, String description, Member manager, String imgUrl) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.description = description;
        this.participants = 0;
        this.manager = manager;
        this.imgUrl = imgUrl;
    }

}
