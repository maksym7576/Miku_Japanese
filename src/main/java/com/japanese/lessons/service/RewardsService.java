package com.japanese.lessons.service;

import com.japanese.lessons.models.third.Rewards;
import com.japanese.lessons.models.ETargetType;
import com.japanese.lessons.repositories.IRewardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RewardsService {

    @Autowired
    private IRewardsRepository iRewardsRepository;

    public List<Rewards> getRewardsByTarget(ETargetType ETargetType, Long targetId) {
        return iRewardsRepository.findByTargetTypeAndTargetId(ETargetType, targetId);
    }
}
