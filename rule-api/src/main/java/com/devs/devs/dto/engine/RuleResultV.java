package com.devs.devs.dto.engine;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * 规则执行结果
 *
 * @author 松梁
 * @date 2021/11/26
 */
@Data
@Builder
public class RuleResultV {
    /**
     * 事项
     */
    private String ruleName;
    /**
     * 检查结果
     */
    private Boolean trigger;

    /**
     * 条件组
     */
    private String conditions;

    /**
     * 当前规则下字段的实际值
     */
    private Map<String, Object> facts;
}
