package com.swyp.kiwoyu.mandalart.domain;

import com.swyp.kiwoyu.global.common.BaseEntity;
import com.swyp.kiwoyu.mandalart.dto.PostMandalartDto;
import com.swyp.kiwoyu.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "mandalart")
@Getter
@Setter
public class Mandalart extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String title;

    @Column(columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String category;

    // Foreign key
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    private Integer level=1;
    private Integer exp=0;

    private Boolean levelUp=false;

    public Mandalart(PostMandalartDto dto){
        this.setTitle(dto.getTitle());
        this.setCategory(dto.getCategories().toString().replace("[","").replace("]",""));
    }

    public Mandalart() {

    }
}
