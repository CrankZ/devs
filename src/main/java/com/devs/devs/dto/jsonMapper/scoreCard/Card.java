package com.devs.devs.dto.jsonMapper.scoreCard;

import lombok.Data;

import java.util.List;

@Data
public class Card {
  private String field;
  private List<CardCondition> conditionds;
}
