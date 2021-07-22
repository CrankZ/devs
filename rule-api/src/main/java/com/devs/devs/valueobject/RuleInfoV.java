package com.devs.devs.valueobject;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RuleInfoV {

    /**
     * 主键
     */
    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 规则名
     */
    private String ruleName;

    /**
     * 规则类型
     * RULE_SET:规则组
     * DECISION_TREE:决策树
     * SCORE_CAR:评分卡
     */
    private String ruleType;

    /**
     * 规则所属场景
     * 用来给规则分类
     */
    private Long sceneId;

    /**
     * 结构体
     */
    private String ruleJson;

    /**
     * 规则描述
     */
    private String ruleDesc;

}