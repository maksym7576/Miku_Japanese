package com.japanese.lessons.repositories;

import com.japanese.lessons.models.sixsth.FileRecords;
import com.japanese.lessons.models.third.Rewards;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRewardsRepository extends JpaRepository<Rewards, Long> {
    List<Rewards> findAllById(Iterable<Long> ids);
}
