package com.devs.devs.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 规则引擎中值的类型
 * 期望值和真实值
 */
@AllArgsConstructor
@Getter
public enum ValueTypeEnum {
    EXPECT("$EXPECT"), FACT("$FACT");

    /**
     * 占位符
     */
    private String placeholder;
}
