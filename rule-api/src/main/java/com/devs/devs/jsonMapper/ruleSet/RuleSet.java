package com.devs.devs.jsonMapper.ruleSet;

import com.devs.devs.jsonMapper.simple.Rule;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 规则集
 *
 * @author 松梁
 * @date 2021/7/16
 */
@Data
@Accessors(chain = true)
public class RuleSet {
    /**
     * 场景
     */
    private String sceneId;

    /**
     * 名称
     */
    private String ruleSetName;

    /**
     * 描述
     */
    private String desc;

    /**
     * 规则
     */
    private List<Rule> rules;
}
