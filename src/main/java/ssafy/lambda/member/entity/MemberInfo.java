package ssafy.lambda.member.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class MemberInfo {

    @Id
    @Column(name = "member_id")
    Long memberId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "member_id")
    Member member;

    @Column
    Integer point;

    @Column
    Date createdAt;

    @Column
    String profileImgUrl;
}
