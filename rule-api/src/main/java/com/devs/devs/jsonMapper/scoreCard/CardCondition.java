package com.devs.devs.jsonMapper.scoreCard;

import com.devs.devs.jsonMapper.action.Condition;
import lombok.Data;

@Data
public class CardCondition {
    private Integer row;
    private Condition condition;
    private Integer score;
}
