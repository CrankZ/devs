package com.devs.devs.jsonMapper.action;

import lombok.Data;

import java.util.List;

@Data
public class Condition {
    /**
     * 执行时会被替换成fact
     * aka:fact
     */
    private String fieldCode;
    private String operator;
    private Object value;

    // 递归
    private List<Condition> and;
    private List<Condition> or;

    // 只有规则执行完毕才有用
    private Boolean trigger;
    private Object fact;
}
