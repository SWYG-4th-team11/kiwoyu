package com.swyp.kiwoyu.goal.domain;

import com.swyp.kiwoyu.global.common.BaseEntity;
import com.swyp.kiwoyu.mandalart.domain.Mandalart;
import com.swyp.kiwoyu.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "goal")
@Getter
@Setter
public class Goal extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(255)")
    private String type;

    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String content;

    // 대목표 -> null / 중목표 -> 상위 대목표의 ID / 소목표 -> 상위 중목표의 ID
    private Long parentGoalId;

    // Foreign key
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    // Foreign key
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="mandalart_id")
    private Mandalart mandalart;

}
