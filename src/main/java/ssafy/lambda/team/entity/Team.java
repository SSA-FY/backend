package ssafy.lambda.team.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import ssafy.lambda.membership.entity.Membership;

@Entity
@Getter
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;

    @Column
    private String teamName;

    @Column
    private String description;

    @Column
    private LocalDateTime createdAt;

    @Column
    private Integer participants;

    @OneToMany(mappedBy = "team", orphanRemoval = true)
    private List<Membership> memberships = new ArrayList<>();

    protected Team() {
    }

    @Builder
    public Team(Long teamId, String teamName, String description) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.description = description;
        this.createdAt = LocalDateTime.now();
        this.participants = 0;
    }

    public void update(String teamName, String description) {
        this.teamName = teamName;
        this.description = description;
    }
}
