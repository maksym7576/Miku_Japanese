package com.japanese.lessons.repositories;

import com.japanese.lessons.models.User.UserIncorrectAnswers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUserIncorrectAnswers extends JpaRepository<UserIncorrectAnswers, Long> {
    List<UserIncorrectAnswers> findAllByUserId(Long userId);
}
