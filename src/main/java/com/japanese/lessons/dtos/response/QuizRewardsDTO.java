package com.japanese.lessons.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuizRewardsDTO {
  private String type;
  private Object reward;
  private Object image;
}
