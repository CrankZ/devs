package com.devs.devs.valueobject;

import com.devs.devs.jsonMapper.simple.Rule;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * 规则执行结果
 *
 * @author 松梁
 * @date 2021/7/16
 */
@Data
@SuperBuilder
public class RuleResult extends BaseResult {
    /**
     * 规则结构体（包括是否触发）
     */
    private Rule ruleV;
}
