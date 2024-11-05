package com.japanese.lessons.models.User;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserIncorrectAnswers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long objectId;

    @Column
    private String type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
