package ssafy.lambda.team.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import ssafy.lambda.membership.entity.Membership;

@Entity
@Getter
@Setter
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long teamId;

    @Column
    String teamName;

    @Column
    String description;

    @Column
    Date createdAt;

    @Column
    Integer participants;
    @OneToMany(mappedBy = "team")
    List<Membership> memberships = new ArrayList<>();

}
