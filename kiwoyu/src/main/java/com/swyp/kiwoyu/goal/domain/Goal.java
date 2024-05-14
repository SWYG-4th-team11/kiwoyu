package com.swyp.kiwoyu.goal.domain;

import com.swyp.kiwoyu.global.common.BaseEntity;
import com.swyp.kiwoyu.goal.dto.UpdateGoalRequestDto;
import com.swyp.kiwoyu.mandalart.domain.Mandalart;
import com.swyp.kiwoyu.mandalart.dto.PostMandalartDto;
import com.swyp.kiwoyu.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "goal")
@Getter
@Setter
public class Goal extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(255)")
    private String title;

    // main, middle, small
    @Column(columnDefinition = "VARCHAR(255)")
    private String type;

    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String content;

    // 대목표 -> null / 중목표 -> 상위 대목표의 ID / 소목표 -> 상위 중목표의 ID
    private Long parentGoalId;

    private Date goalDate;

    private Boolean isAchieved;

    private Boolean isAppliedToExp=false;

    // Foreign key
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    // Foreign key
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="mandalart_id")
    private Mandalart mandalart;

    public Goal(Goal goal, UpdateGoalRequestDto dto){
        this.id = dto.getId();
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.goalDate = dto.getGoalDate();
        this.isAchieved = dto.isAchieved();

        this.type=goal.getType();
        this.parentGoalId=goal.getParentGoalId();
        this.user=goal.getUser();
        this.mandalart=goal.getMandalart();
    }

    public Goal(PostMandalartDto dto, Mandalart m){
        this.setType("main");
        this.setTitle(dto.getTitle());
        this.setContent("");
        this.setGoalDate(dto.getDue());
        this.setIsAchieved(false);
        this.mandalart = m;
    }

    public Goal() {

    }
}
