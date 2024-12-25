package com.japanese.lessons.dtos.request;

import com.japanese.lessons.dtos.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FinalExerciseRequestDTO {

    private Long userId;
    private Long exerciseId;
    private List<UserResponseDTO> userResponsesList;
}
