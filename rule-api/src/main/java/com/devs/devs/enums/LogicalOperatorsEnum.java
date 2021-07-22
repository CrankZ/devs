package com.devs.devs.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 逻辑运算符
 */
@Getter
@AllArgsConstructor
public enum LogicalOperatorsEnum {
    AND("与", "&&"), OR("或", "||");

    private String name;

    private String operator;

}
