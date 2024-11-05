package com.japanese.lessons.service;

import com.japanese.lessons.models.User.UserIncorrectAnswers;
import com.japanese.lessons.repositories.IUserIncorrectAnswers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserIncorrectAnswersService {

    @Autowired
    IUserIncorrectAnswers iUserIncorrectAnswers;

    public void saveIncorrectAnswer(UserIncorrectAnswers userIncorrectAnswers) {
        iUserIncorrectAnswers.save(userIncorrectAnswers);
    }
    public void saveAllIncorrectAnswers(List<UserIncorrectAnswers> userIncorrectAnswersList) {
        iUserIncorrectAnswers.saveAll(userIncorrectAnswersList);
    }
}