package com.swyp.kiwoyu.routine.domain;

import com.swyp.kiwoyu.global.common.BaseEntity;
import com.swyp.kiwoyu.mandalart.domain.Mandalart;
import com.swyp.kiwoyu.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "routine")
@Getter
@Setter
public class Routine extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String title;

    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String memo;

    private Boolean isChecked;
    private Boolean isAppliedToExp=false;

    @Temporal(TemporalType.DATE)
    private Date routine_date;

    // Foreign key
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    // Foreign key
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="mandalart_id")
    private Mandalart mandalart;

}
