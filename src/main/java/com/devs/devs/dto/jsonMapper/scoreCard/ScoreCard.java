package com.devs.devs.dto.jsonMapper.scoreCard;

import lombok.Data;

@Data
public class ScoreCard {
  private String application;
  private String scoreCardName;
  @Deprecated
  private Boolean isWeight;
}
