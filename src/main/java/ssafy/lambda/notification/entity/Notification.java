package ssafy.lambda.notification.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import ssafy.lambda.global.common.BaseEntity;
import ssafy.lambda.member.entity.Member;

@Entity
@Getter
@DiscriminatorColumn(name = "DTYPE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Notification extends BaseEntity {

    @Id
    @Column(name = "notification_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    protected Notification() {
    }

    public Notification(Member member) {
        this.member = member;
    }
}
