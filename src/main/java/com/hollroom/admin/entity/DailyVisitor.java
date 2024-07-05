package com.hollroom.admin.entity;

import com.hollroom.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
public class DailyVisitor {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDate visitDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public DailyVisitor(UserEntity user, LocalDate today) {
        this.user = user;
        this.visitDate = today;
    }
}
