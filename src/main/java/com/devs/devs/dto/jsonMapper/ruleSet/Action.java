package com.devs.devs.dto.jsonMapper.ruleSet;

import com.devs.devs.consts.ActionEnum;
import lombok.Data;

/**
 * 规则执行动作
 * 一般是异步执行，不影响主流程
 */
@Data
public class Action {
  private ActionEnum type;
  private Object value;

  // 以下是执行完毕才会用到的值
  private Boolean trigger;
  private Object result;
}
