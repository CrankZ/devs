package com.devs.devs.dto.jsonMapper.scoreCard;

import com.devs.devs.dto.jsonMapper.ruleSet.Condition;
import lombok.Data;

@Data
public class CardCondition {
  private Integer row;
  private Condition condition;
  private Integer score;
}
