package com.devs.devs.dto.engine;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 规则引擎执行出参
 *
 * @author 松梁
 * @date 2021/11/27
 */
@Data
@Builder
public class RuleEngineExecuteResponse {
    /**
     * 模板
     */
    private Long templateId;

    /**
     * 规则执行入参（通过浏览器传入）
     */
    private Map<String, Object> params;

    /**
     * 规则执行结果
     */
    private List<RuleResultV> ruleResult;
}
