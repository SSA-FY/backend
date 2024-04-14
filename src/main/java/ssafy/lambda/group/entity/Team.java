package ssafy.lambda.group.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ssafy.lambda.membership.entity.Membership;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long teamId;

    @Column
    String groupName;

    @Column
    String description;

    @Column
    Date createdAt;

    @Column
    Integer participants;
    @OneToMany(mappedBy = "team")
    List<Membership> memberships;

}
