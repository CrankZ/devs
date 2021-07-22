package com.devs.devs.jsonMapper.action;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 规则执行动作
 * 一般是异步执行，不影响主流程
 */
@Data
@Accessors(chain = true)
public class Action {
    private String type;
    private Object value;

    // 以下是执行完毕才会用到的值
    private Boolean trigger;
    private Object result;
}
