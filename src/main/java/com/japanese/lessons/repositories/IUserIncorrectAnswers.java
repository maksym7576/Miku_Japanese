package com.japanese.lessons.repositories;

import com.japanese.lessons.models.User.UserIncorrectAnswers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserIncorrectAnswers extends JpaRepository<UserIncorrectAnswers, Long> {
}
