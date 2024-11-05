package com.japanese.lessons.repositories;

import com.japanese.lessons.models.Rewards;
import com.japanese.lessons.models.TargetType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRewardsRepository extends JpaRepository<Rewards, Long> {
    List<Rewards> findByTargetTypeAndTargetId(TargetType targetType, Long targetId);
}
