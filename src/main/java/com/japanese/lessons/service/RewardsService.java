package com.japanese.lessons.service;

import com.japanese.lessons.dtos.response.FinalExerciseResponseDTO;
import com.japanese.lessons.dtos.response.models.RewardsDTO;
import com.japanese.lessons.models.third.Rewards;
import com.japanese.lessons.repositories.IRewardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RewardsService {

    @Autowired
    private IRewardsRepository iRewardsRepository;

   private List<Rewards> getRewardsListBuIdsList(List<Long> ids) {
     return iRewardsRepository.findAllById(ids);
   }

   private RewardsDTO formRewardDTO(Rewards rewards) {
       return new RewardsDTO(rewards.getId(), rewards.getName(), rewards.getDescription(), rewards.getType());
   }

   private List<RewardsDTO> formRewardsDTOList(List<Rewards> rewardsList) {
       List<RewardsDTO> rewardsDTOList = new ArrayList<>();
       for (Rewards rewards : rewardsList) {
           rewardsDTOList.add(formRewardDTO(rewards));
       }
       return rewardsDTOList;
   }

   public List<RewardsDTO> getRewardsDTOListByIdsList(List<Long> idsList) {
       return new ArrayList<>(formRewardsDTOList(getRewardsListBuIdsList(idsList)));
   }

}
