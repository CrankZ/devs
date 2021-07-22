package com.devs.devs.jsonMapper.scoreCard;

import lombok.Data;

@Data
public class ScoreCard {
    private String application;
    private String scoreCardName;
    @Deprecated
    private Boolean isWeight;
}
