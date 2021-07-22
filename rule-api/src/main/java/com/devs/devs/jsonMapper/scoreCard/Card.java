package com.devs.devs.jsonMapper.scoreCard;

import lombok.Data;

import java.util.List;

@Data
public class Card {
    private String field;
    private List<CardCondition> conditionds;
}
