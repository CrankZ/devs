package com.devs.devs.dto.engine;

import com.devs.devs.jsonMapper.action.Action;
import com.devs.devs.valueobject.RuleResult;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 可以弄个快照版本，用来存储当时执行的结果
 * 单次执行后，返回给前端的结果
 *
 * @author 松梁
 * @date 2021/7/16
 */
@Data
@Builder
public class ExecuteRuleResponse {
    /**
     * 场景
     */
    private Long sceneId;

    /**
     * 字段真实的值
     */
    private Map<String, Object> facts;

    /**
     * 规则列表，一个场景下面可能会有多个规则集
     */
    private List<RuleResult> ruleResults;

    /**
     * 执行后触发的内容
     */
    private List<Action> actions;
}
