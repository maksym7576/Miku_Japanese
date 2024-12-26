package com.japanese.lessons.repositories;


import com.japanese.lessons.models.User.EFinishedTypes;
import com.japanese.lessons.models.User.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface IUserProgressRepository extends JpaRepository<UserProgress, Long> {
    @Query("SELECT up FROM UserProgress up WHERE up.eFinishedTypes = :type AND up.lessonId = :lessonId AND up.userId = :userId")
    List<UserProgress> findByEFinishedTypesAndLessonIdAndUserId(
            @Param("type") EFinishedTypes eFinishedTypes,
            @Param("lessonId") Long lessonId,
            @Param("userId") Long userId
    );
    @Query("SELECT up FROM UserProgress up WHERE up.eFinishedTypes = :type AND up.userId = :userId")
    List<UserProgress> findByEFinishedTypesAndUserId(
            @Param("type") EFinishedTypes eFinishedTypes,
            @Param("userId") Long userId
    );
}
