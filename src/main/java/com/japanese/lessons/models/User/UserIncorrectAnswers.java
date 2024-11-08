package com.japanese.lessons.models.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
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
