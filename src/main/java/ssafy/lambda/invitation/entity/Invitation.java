package ssafy.lambda.invitation.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ssafy.lambda.global.common.BaseEntity;
import ssafy.lambda.member.entity.Member;
import ssafy.lambda.notification.entity.NotificationDetail.InvitationNotification;
import ssafy.lambda.team.entity.Team;

@Entity
@Getter
public class Invitation extends BaseEntity {

    @Id
    @Column(name = "invitation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
    @Setter
    @Column(name = "invalidate_at")
    private Instant invalidateAt;

    @OneToOne(mappedBy = "invitation", orphanRemoval = true)
    private InvitationNotification invitationNotification;


    @OneToOne(mappedBy = "invitation", cascade = CascadeType.ALL, orphanRemoval = true)
    private InvitationNotification notification;

    protected Invitation() {
    }

    @Builder
    public Invitation(Member member, Team team) {
        this.member = member;
        this.team = team;
        this.invalidateAt = Instant.now()
                                   .plus(7, ChronoUnit.DAYS);
    }
}
