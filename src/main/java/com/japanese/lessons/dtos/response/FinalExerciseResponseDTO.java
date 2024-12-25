package com.japanese.lessons.dtos.response;

import com.japanese.lessons.dtos.response.models.RewardsDTO;
import com.japanese.lessons.models.third.Rewards;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FinalExerciseResponseDTO {

    private int percentage;
    private int exp;
    private List<RewardsDTO> rewardsList;
}
